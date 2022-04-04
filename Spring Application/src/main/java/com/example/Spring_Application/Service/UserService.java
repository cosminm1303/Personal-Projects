package com.example.Spring_Application.Service;

import com.example.Spring_Application.DTO.UserDTO;
import com.example.Spring_Application.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User saveUser(UserDTO userDTO);
}
