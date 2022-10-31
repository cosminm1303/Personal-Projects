package com.example.onlineshop.service;

import com.example.onlineshop.models.Address;
import com.example.onlineshop.repository.AddressRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepo addressRepo;

    public List<Address> getAllByUserId(Long id) {
        return addressRepo.findAllByUserId(id);
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepo.findById(id);
    }

    public Address saveAddress(Address address) {
        return addressRepo.save(address);
    }

    public void deleteAddressById(Long id) {
        addressRepo.deleteById(id);
    }
}
