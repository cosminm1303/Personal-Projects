package com.example.onlineshop.repository;

import com.example.onlineshop.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderProductRepo extends JpaRepository<OrderProduct, Long> {
}
