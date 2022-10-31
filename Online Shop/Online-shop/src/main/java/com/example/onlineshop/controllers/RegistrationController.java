package com.example.onlineshop.controllers;

import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    UserDetailsServiceImpl userDetailsService;

    @GetMapping()
    public String getRegForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "registration";
    }

    @PostMapping()
    public String register(@ModelAttribute("user") UserDTO userDTO) {
        userDetailsService.saveUser(userDTO);
        return "redirect:/registration?success";
    }
}
