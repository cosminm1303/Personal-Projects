package com.example.Spring_Application.Service;

import com.example.Spring_Application.DTO.UserDTO;
import com.example.Spring_Application.Model.Role;
import com.example.Spring_Application.Model.User;
import com.example.Spring_Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

private final UserRepository userRepository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;

@Autowired
public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
    this.userRepository=userRepository;
    this.bCryptPasswordEncoder=bCryptPasswordEncoder;
}

    @Override
    public User saveUser(UserDTO userDTO) {
        User user=new User(userDTO.getFirstName(),userDTO.getLastName(),userDTO.getEmail(),bCryptPasswordEncoder.encode(userDTO.getPassword()), Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoleList()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
