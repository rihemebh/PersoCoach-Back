package com.website.persocoach.Controllers;


import com.website.persocoach.Models.*;
import com.website.persocoach.repositories.RoleRepository;
import com.website.persocoach.repositories.UserRepository;
import com.website.persocoach.services.JwtUtils;
import com.website.persocoach.services.UserDetailsImpl;
import com.website.persocoach.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private PasswordEncoder passwordEncoder;


    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest){
        Authentication auth = null;

        try{
             auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException badCredentialsException){
            return new ResponseEntity<String>("bad credentials", HttpStatus.BAD_REQUEST);
        }catch (DisabledException disabledException){
            return new ResponseEntity<String>("disabled", HttpStatus.BAD_REQUEST);
        }catch (LockedException lockedException){
            return new ResponseEntity<String>("locked", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<String>("error authenticating", HttpStatus.BAD_REQUEST);
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());

        //UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));


//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        }catch (Exception e){
//            return new ResponseEntity<String>("error authenticating", HttpStatus.FORBIDDEN);
//        }
//        UserDetails currentUser = userDetailsServiceImpl.loadUserByUsername(username);
//        String generatedToken = jwtUtils.generateToken(currentUser);
//
//        List<String> roles = currentUser.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        return new ResponseEntity<JwtResponse>(new JwtResponse(generatedToken,
//                                                                userDetailsServiceImpl.getId(username),
//                                                                username,
//                                                                roles), HttpStatus.OK);

    }

    @PostMapping("/sign-up")
    private ResponseEntity<?> createUser(@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String email = authenticationRequest.getEmail();
        //User existingUser = userRepository.findByUsername(username);
        if(userRepository.existsByUsername(authenticationRequest.getUsername())){
            return new ResponseEntity<String>("user with username "+username+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(authenticationRequest.getEmail())){
            return new ResponseEntity<String>("user with Email "+email+" already exists", HttpStatus.BAD_REQUEST);
        }

        User user = new User(email,username,passwordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleType.ROLE_CLIENT)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        try{
            userRepository.save(user);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating user", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("user has been successfully signed up", HttpStatus.OK);

    }
}
