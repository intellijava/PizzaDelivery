package com.example.pizzadelivery.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PizzaRestControllerSpringBootTest {

    @Autowired
    private PizzaRestController pizzaRestController;

    @Test
    void getAllPizzas(){
        assertThat(pizzaRestController.getAllPizzas()).isEmpty();
    }
}