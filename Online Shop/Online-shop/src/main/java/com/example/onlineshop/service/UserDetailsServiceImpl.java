package com.example.onlineshop.service;

import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.models.MyUserDetails;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.models.User;
import com.example.onlineshop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(UserDTO userDTO) {
        User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPhoneNumber(), bCryptPasswordEncoder.encode(userDTO.getPassword()), Arrays.asList(new Role("ROLE_USER")));
        return userRepo.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find the email");
        }
        return new MyUserDetails(user);
    }
}
