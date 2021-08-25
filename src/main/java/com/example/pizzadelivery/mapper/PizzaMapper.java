package com.example.pizzadelivery.mapper;

import com.example.pizzadelivery.dtos.PizzaDto;
import com.example.pizzadelivery.entities.Pizza;
import org.springframework.stereotype.Component;

public class PizzaMapper {

    public static Pizza dtoToEntity(PizzaDto pizzaDto){
        return new Pizza(pizzaDto.id, pizzaDto.price, pizzaDto.diameter, pizzaDto.ingredients, pizzaDto.type);
    }

    public static PizzaDto entityToDto(Pizza pizza){
        return new PizzaDto(pizza.getId(), pizza.getPrice(), pizza.getDiameter(), pizza.getIngredients(), pizza.getType());
    }

}
