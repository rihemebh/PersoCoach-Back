package com.website.persocoach.Controllers;


import com.website.persocoach.Models.AuthenticationRequest;
import com.website.persocoach.Models.User;
import com.website.persocoach.repositories.UserRepository;
import com.website.persocoach.services.JwtUtils;
import com.website.persocoach.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserService userService;
    @Autowired private JwtUtils jwtUtils;

    @PostMapping("/api/sign-in")
    private ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (Exception e){
            return new ResponseEntity<String>("error authenticating", HttpStatus.BAD_GATEWAY);
        }
        UserDetails currentUser = userService.loadUserByUsername(username);
        String generatedToken = jwtUtils.generateToken(currentUser);
        return new ResponseEntity<String>("user has been successfully signed in.\nGenerated token: "+generatedToken,
                HttpStatus.OK);

    }

    @PostMapping("/api/sign-up")
    private ResponseEntity<?> createUser(@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        User user = new User(username,password);
        User existingUser = userRepository.findByUsername(username);
        if(existingUser != null){
            return new ResponseEntity<String>("user with username "+username+" already exists", HttpStatus.BAD_REQUEST);
        }
        try{
            userRepository.save(user);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating user", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("user has been successfully signed up", HttpStatus.OK);

    }
}
