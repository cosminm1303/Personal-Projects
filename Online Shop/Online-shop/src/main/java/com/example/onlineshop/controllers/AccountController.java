package com.example.onlineshop.controllers;

import com.example.onlineshop.DTO.AddressDTO;
import com.example.onlineshop.models.Address;
import com.example.onlineshop.models.MyUserDetails;
import com.example.onlineshop.models.OrderDetails;
import com.example.onlineshop.models.User;
import com.example.onlineshop.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private CategoryServiceImp categoryServiceImp;
    private ProductServiceImpl productService;
    private ShoppingCartService shoppingCartService;
    private UserDetailsServiceImpl userDetailsService;
    private AddressService addressService;
    private OrderDetailsService orderDetailsService;

    @GetMapping("/details")
    public String getDetails(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        User user = userDetailsService.getUserByEmail(myUserDetails.getUsername());
        List<Address> addresses = addressService.getAllByUserId(user.getId());
        model.addAttribute("user", user);
        if (!addresses.isEmpty())
            model.addAttribute("address", addresses.get(0));
        return "accountDetails";
    }

    @ModelAttribute("addressDTO")
    public AddressDTO addresDTO() {
        return new AddressDTO();
    }

    @GetMapping("/address/add")
    public String getAddressAddRender() {
        return "addAddress";
    }

    @PostMapping("/address/add")
    public String getAddressAdd(@ModelAttribute("addressDTO") AddressDTO addressDTO,
                                @AuthenticationPrincipal MyUserDetails myUserDetails,
                                Model model) {
        User user = userDetailsService.getUserByEmail(myUserDetails.getUsername());
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setCounty(addressDTO.getCounty());
        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setUser(user);

        addressService.saveAddress(address);
        List<Address> addresses = addressService.getAllByUserId(user.getId());
        if (!addresses.isEmpty())
            model.addAttribute("address", addresses.get(0));
        model.addAttribute("user", user);
        return "accountDetails";
    }

    @GetMapping("/address")
    public String getAddress(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        User user = userDetailsService.getUserByEmail(myUserDetails.getUsername());
        List<Address> addresses = addressService.getAllByUserId(user.getId());
        if (!addresses.isEmpty())
            model.addAttribute("address", addresses.get(0));
        model.addAttribute("addresses", addresses);
        model.addAttribute("user", user);
        return "myAddress";

    }

    @GetMapping("/address/update/{id}")
    public String addressUpdate(@PathVariable Long id, Model model) {
        Optional<Address> address = addressService.getAddressById(id);
        if (address.isPresent()) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(address.get().getId());
            addressDTO.setCountry(address.get().getCountry());
            addressDTO.setCounty(address.get().getCounty());
            addressDTO.setCity(address.get().getCity());
            addressDTO.setStreet(address.get().getStreet());
            addressDTO.setNumber(address.get().getNumber());
            addressDTO.setPostalCode(address.get().getPostalCode());
            model.addAttribute("addressDTO", addressDTO);
            return "addAddress";
        } else {
            return "404";
        }
    }

    @GetMapping("/address/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return "redirect:/account/address";
    }

    @GetMapping("/orders")
    public String getOrders(Model model){
        List<OrderDetails> orders=orderDetailsService.getOrders();
        model.addAttribute("orders",orders);
        return "orders";
    }

}
