package com.example.onlineshop.repository;

import com.example.onlineshop.models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCatRepo extends JpaRepository<SubCategory, Long> {
}
