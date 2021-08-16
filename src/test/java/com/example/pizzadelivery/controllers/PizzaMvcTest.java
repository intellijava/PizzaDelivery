package com.example.pizzadelivery.controllers;

import com.example.pizzadelivery.repositories.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {PizzaRestController.class})
class PizzaMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PizzaRestController pizzaRestController;
    @MockBean
    private PizzaRepository pizzaRepository;

    @Test
    void getOnePizza() throws Exception {
        mockMvc.perform(get("/pizzas"))
                .andExpect(status().is(200));
    }

}