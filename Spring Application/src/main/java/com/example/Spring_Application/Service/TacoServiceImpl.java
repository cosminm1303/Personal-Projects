package com.example.Spring_Application.Service;

import com.example.Spring_Application.Model.Taco;
import com.example.Spring_Application.Repository.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TacoServiceImpl implements TacoService {

    private final TacoRepository tacoRepository;

    @Autowired
   public TacoServiceImpl(TacoRepository tacoRepository){this.tacoRepository=tacoRepository;}

    @Override
    public Taco saveAllTacos(Taco taco) {
        return tacoRepository.save(taco);
    }
}
