package com.website.persocoach.controllers;

import com.website.persocoach.Models.*;
import com.website.persocoach.payload.request.AuthenticationRequest;
import com.website.persocoach.repositories.AdminRepository;
import com.website.persocoach.repositories.ClientRepository;
import com.website.persocoach.repositories.CoachRepository;
import com.website.persocoach.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired private AdminRepository adminRepository;
    @Autowired private CoachRepository coachRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;


    @PostMapping("/coach/new")
    public ResponseEntity<?> newCoach(@RequestParam String name,@RequestParam String gender, @RequestParam String type,@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String email = authenticationRequest.getEmail();

        if(adminRepository.existsByUsername(authenticationRequest.getUsername())
                || clientRepository.existsByUsername(authenticationRequest.getUsername())
                || coachRepository.existsByUsername(authenticationRequest.getUsername())
        ){
            return new ResponseEntity<String>("Username "+username+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(adminRepository.existsByEmail(authenticationRequest.getEmail())
                || clientRepository.existsByUsername(authenticationRequest.getEmail())
                || coachRepository.existsByUsername(authenticationRequest.getEmail())
        ){
            return new ResponseEntity<String>("Email "+email+" already exists", HttpStatus.BAD_REQUEST);
        }



        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleType.ROLE_COACH)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found."));
        roles.add(userRole);
        Coach coach_user = new Coach(email,username,passwordEncoder.encode(password),roles);
        coach_user.setName(name);
        coach_user.setType(type);
        coach_user.setGender(gender);
        if(gender.equals("Women")){
            if(type.equals("Sport"))
            coach_user.setUrl("https://www.ekyno.com/wp-content/uploads/elementor/thumbs/coach-perso-image-libre-o8tzjqh5l4w9r7koycnmk9kosk8y8mm0ybpwixkt2s.jpg");
            else   coach_user.setUrl("https://www.wholeintent.com/media/k2/items/cache/eb6c7c01c4e98e1f2578f9959463b973_XL.jpg");
        }

        else{
            if(type.equals("Sport"))
                coach_user.setUrl("https://www.musculaction.com/images/intro-coach-sportif.jpg");
            else   coach_user.setUrl("https://img.themanual.com/image/themanual/athletic-man-eating-banana-after-workout-outdoors.jpg");
        }
        try{
            coachRepository.save(coach_user);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating coach", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("coach has been successfully signed up", HttpStatus.CREATED);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newAdmin(@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String email = authenticationRequest.getEmail();

        if(adminRepository.existsByUsername(authenticationRequest.getUsername())
                || clientRepository.existsByUsername(authenticationRequest.getUsername())
                || coachRepository.existsByUsername(authenticationRequest.getUsername())
        ){
            return new ResponseEntity<String>("Username "+username+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(adminRepository.existsByEmail(authenticationRequest.getEmail())
                || clientRepository.existsByUsername(authenticationRequest.getEmail())
                || coachRepository.existsByUsername(authenticationRequest.getEmail())
        ){
            return new ResponseEntity<String>("Email "+email+" already exists", HttpStatus.BAD_REQUEST);
        }


        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found."));
        roles.add(userRole);
        Admin admin = new Admin(email,username,passwordEncoder.encode(password),roles);
        try{
            adminRepository.save(admin);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating coach", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("admin has been successfully signed up", HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
        public ResponseEntity<String> updateAdmin(@PathVariable String id,
                                                  @RequestParam Optional<String> username,
                                                  @RequestParam Optional<String> email,
                                                  @RequestParam Optional<String> password
                                                  ){
       Admin a =  adminRepository.findById(id).orElse(null);

       if(a != null){

           if(username.isPresent()){
               String name = username.orElse(null);
               if(!name.equals("")) {
                   a.setUsername(username.orElse(a.getUsername()));
               }
           }
           if(email.isPresent()){
               String mail = email.orElse(null);
               if(!mail.equals(""))
               a.setEmail(email.orElse(a.getEmail()));
           }
           if(password != null){
               String pass = password.orElse(null);
               if(!pass.equals("")){

                   a.setPassword(passwordEncoder.encode(password.orElse(null)));
               }

           }

           adminRepository.save(a);


       }else{
           return new ResponseEntity<String>("error updating", HttpStatus.BAD_REQUEST);
       }


        return null;
    }
}



