package com.example.pizzadelivery.mapper;

import com.example.pizzadelivery.dtos.PizzaDto;
import com.example.pizzadelivery.entities.Pizza;

public class PizzaMapper {

    public static Pizza dtoToEntity(PizzaDto pizzaDto){
        return new Pizza(pizzaDto.id, pizzaDto.diameter, pizzaDto.sauce, pizzaDto.sauce);
    }

    public static PizzaDto entityToDto(Pizza pizza){
        return new PizzaDto(pizza.getID(), pizza.getDIAMETER(), pizza.getSAUCE(), pizza.getTYPE());
    }

}
