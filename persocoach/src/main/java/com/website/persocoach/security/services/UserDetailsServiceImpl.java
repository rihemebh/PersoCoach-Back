package com.website.persocoach.security.services;

import com.website.persocoach.Models.Admin;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.User;
import com.website.persocoach.repositories.AdminRepository;
import com.website.persocoach.repositories.ClientRepository;
import com.website.persocoach.repositories.CoachRepository;
import com.website.persocoach.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private ClientRepository clientRepository;
    @Autowired private CoachRepository coachRepository;
    @Autowired private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User client_user = clientRepository.findByUsername(username);
        if(client_user == null){
            Coach coach_user = coachRepository.findByUsername(username);
            if(coach_user == null) {
                Admin admin_user = adminRepository.findByUsername(username);
                if(admin_user == null){
                    throw new UsernameNotFoundException("User with username "+username+" does not exist!");
                }else{
                    User user_admin = new User(
                            admin_user.getEmail(),
                            admin_user.getUsername(),
                            admin_user.getPassword(),
                            admin_user.getRoles());
                    user_admin.setId(admin_user.getId());
                    return UserDetailsImpl.build(user_admin);
                }
            }else{
                User user_coach = new User(
                        coach_user.getEmail(),
                        coach_user.getUsername(),
                        coach_user.getPassword(),
                        coach_user.getRoles());
                user_coach.setId(coach_user.getId());
                return UserDetailsImpl.build(user_coach);
            }
        }else{
            User user_client = new User(
                    client_user.getEmail(),
                    client_user.getUsername(),
                    client_user.getPassword(),
                    client_user.getRoles());
            user_client.setId(client_user.getId());
            return UserDetailsImpl.build(user_client);
        }
    }
}
