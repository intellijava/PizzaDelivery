package com.example.pizzadelivery.repositories;

import com.example.pizzadelivery.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository< Pizza, Integer > {

    List<Pizza> findAllByIngredientsContaining(String ingredient);

    List< Pizza > findAllByDiameter(Integer diameter);

    List <Pizza > findAllByType(String type);

    List <Pizza > findAllByPrice(Integer Price);

}