package com.example.onlineshop.repository;

import com.example.onlineshop.models.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;

@Repository
public interface OrderAddressRepo extends JpaRepository<OrderAddress, Long> {
}
