package com.website.persocoach.services;

import com.website.persocoach.Models.User;
import com.website.persocoach.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null)
            return null;

        String usrnm = user.getUsername();
        String pwd = user.getPassword();
        return new org.springframework.security.core.userdetails.User(usrnm,pwd,new ArrayList<>());
    }
}
