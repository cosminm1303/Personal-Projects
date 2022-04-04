package com.example.Spring_Application.Repository;

import com.example.Spring_Application.Model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient,String> {
}
