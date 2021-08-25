package com.example.pizzadelivery.controllers;

import com.example.pizzadelivery.entities.Pizza;
import com.example.pizzadelivery.mapper.PizzaMapper;
import com.example.pizzadelivery.repositories.PizzaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({@MockBean(PizzaRepository.class), @MockBean(ModelMapper.class), @MockBean(PizzaMapper.class)})
@WebMvcTest(controllers = {PizzaRestController.class})
class PizzaMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PizzaRepository pizzaRepository;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    void getAllPizzasReturnsOnePizza() throws Exception {
        when(pizzaRepository.findAll()).thenReturn(List.of(new Pizza(1, 90, 8, "Cheese,tomatoSauce", "veggie")));
        var result =
                mockMvc.perform(get("/pizzas"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        assertThat(result).isEqualTo("[{\"id\":1,\"price\":90,\"diameter\":8,\"ingredients\":\"Cheese,tomatoSauce\",\"type\":\"veggie\"}]");
    }

    @Test
    public void getOnePizza() throws Exception {
        when(pizzaRepository.findById(1)).thenReturn(of(new Pizza(1, 90, 8, "Cheese,tomatoSauce", "veggie")));

        var result =
                mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/{id}", 1))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        assertThat(result).isEqualTo("{\"id\":1,\"price\":90,\"diameter\":8,\"ingredients\":\"Cheese,tomatoSauce\",\"type\":\"veggie\"}");
    }

    @Test
    public void searchAllByTypeButGetOnePizza() throws Exception {
        when(pizzaRepository.findAllByType("Beef")).thenReturn(List.of(new Pizza(1, 90, 8, "Cheese,tomatoSauce", "Beef")));
        var result =
                mockMvc.perform(get("/pizzas/search?type=Beef"))
                        .andDo(print())
                        .andExpect(status().is(200))
                        .andReturn().getResponse().getContentAsString();
        assertThat(result).isEqualTo("[{\"id\":1,\"price\":90,\"diameter\":8,\"ingredients\":\"Cheese,tomatoSauce\",\"type\":\"Beef\"}]");
    }

    @Test
    public void deletePizzaWithValidId() throws Exception {
        Pizza pizza = new Pizza(1, 90, 8, "Cheese,tomatoSauce", "Beef");
        when(pizzaRepository.findById(pizza.getId())).thenReturn(of(new Pizza(1, 90, 8, "Cheese,tomatoSauce", "Beef")));
        this.mockMvc.perform((delete("/pizzas/{id}", pizza.getId())
                        .contentType(APPLICATION_JSON))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePizzaWithInValidId() throws Exception {

        mockMvc.perform(delete("/pizzas/{id}", 5655))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void putOnePizza() throws Exception {
        Pizza pizza = new Pizza(1, 90, 8, "Cheese,tomatoSauce", "Beef");

        when(pizzaRepository.findById(1)).thenReturn(java.util.Optional.of(pizza));
        when(pizzaRepository.save(pizza)).thenReturn(pizza);
        when(modelMapper.map(pizza, Pizza.class)).thenReturn(pizza);

        String url = "/pizzas/1";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(pizza);

        mockMvc.perform(put(url)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }
}