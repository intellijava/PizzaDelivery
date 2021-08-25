package com.example.pizzadelivery.controllers;

import com.example.pizzadelivery.mapper.PizzaMapper;
import com.example.pizzadelivery.repositories.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PizzaRestControllerTest {

    @Mock
    PizzaRepository pizzaRepository;
    ModelMapper modelMapper;

    @Test
    void getAllPizzas(){
        PizzaRestController pizzaRestController = new PizzaRestController(pizzaRepository, modelMapper);
        assertThat(pizzaRestController.getAllPizzas().isEmpty());
    }

}