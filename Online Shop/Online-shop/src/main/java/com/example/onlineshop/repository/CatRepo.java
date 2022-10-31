package com.example.onlineshop.repository;

import com.example.onlineshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepo extends JpaRepository<Category, Long> {
}
