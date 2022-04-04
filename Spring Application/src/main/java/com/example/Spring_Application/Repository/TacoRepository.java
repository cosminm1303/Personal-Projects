package com.example.Spring_Application.Repository;

import com.example.Spring_Application.Model.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepository extends CrudRepository<Taco,Long> {
}
