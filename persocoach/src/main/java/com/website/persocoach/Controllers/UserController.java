package com.website.persocoach.Controllers;


import com.website.persocoach.Models.User;
import com.website.persocoach.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired UserService userService;

    @GetMapping("/api/user/all")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAll();
        if(users == null)
            return new ResponseEntity<String>("no users found",HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}
