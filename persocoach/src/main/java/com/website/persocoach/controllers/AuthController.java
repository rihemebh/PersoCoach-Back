package com.website.persocoach.controllers;


import com.website.persocoach.Models.*;
import com.website.persocoach.payload.request.AuthenticationRequest;
import com.website.persocoach.payload.response.JwtResponse;
import com.website.persocoach.security.jwt.JwtUtils;
import com.website.persocoach.security.services.UserDetailsImpl;
import com.website.persocoach.security.services.UserDetailsServiceImpl;
import com.website.persocoach.services.AdminService;
import com.website.persocoach.services.ClientService;
import com.website.persocoach.services.CoachesService;
import com.website.persocoach.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AdminService adminService;
    @Autowired private CoachesService coachesService;
    @Autowired private ClientService clientService;
    @Autowired private RoleService roleService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired private JwtUtils jwtUtils;



    @PostMapping("/sign-in")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest){
        Authentication auth = null;
        try{
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());
        }catch(UsernameNotFoundException notFoundException){
            return new ResponseEntity<String>("User does not exist !", HttpStatus.BAD_REQUEST);
        }
        try{
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException badCredentialsException){
            return new ResponseEntity<String>("Incorrect Password", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<String>("error authenticating", HttpStatus.BAD_REQUEST);
        }
        try{
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());
            SecurityContextHolder.getContext().setAuthentication(auth);

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
        }catch(Exception e){
            return new ResponseEntity<String>("Error!" +e+
                    "", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/sign-up")
    @CrossOrigin(origins = "http://localhost:3000")
    private ResponseEntity<?> createUser(@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String email = authenticationRequest.getEmail();
        //User existingUser = userRepository.findByUsername(username);
        if(adminService.existsByUsername(authenticationRequest.getUsername())
                || clientService.existsByUsername(authenticationRequest.getUsername())
                || coachesService.existsByUsername(authenticationRequest.getUsername())
        ){
            return new ResponseEntity<String>("Username "+username+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(adminService.existsByEmail(authenticationRequest.getEmail())
                || clientService.existsByUsername(authenticationRequest.getEmail())
                || coachesService.existsByUsername(authenticationRequest.getEmail())
        ){
            return new ResponseEntity<String>("Email "+email+" already exists", HttpStatus.BAD_REQUEST);
        }

        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(RoleType.ROLE_CLIENT)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found."));
        roles.add(userRole);
        Client client_user = new Client(email,username,passwordEncoder.encode(password),roles);
        try{
            clientService.save(client_user);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating client", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("client has been successfully signed up", HttpStatus.CREATED);
    }
}
