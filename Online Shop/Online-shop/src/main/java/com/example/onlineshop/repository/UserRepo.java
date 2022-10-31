package com.example.onlineshop.repository;

import com.example.onlineshop.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findUserByEmail(String email);
}
