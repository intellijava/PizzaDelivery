package com.example.pizzadelivery.controllers;

import com.example.pizzadelivery.dtos.PizzaDto;
import com.example.pizzadelivery.entities.Pizza;
import com.example.pizzadelivery.mapper.PizzaMapper;
import com.example.pizzadelivery.repositories.PizzaRepository;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PizzaRestController {

    @Autowired
    private final PizzaRepository pizzaRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Value("${spring.cloud.consul.discovery.instance-id}")
    private String id;

    public PizzaRestController(PizzaRepository pizzaRepository, ModelMapper modelMapper) {
        this.pizzaRepository = pizzaRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/pizzas/test")
    public String test() {
        return id;
    }

    @GetMapping("/pizzas")
    List< Pizza > getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @GetMapping("/pizzas/{id}")
    Optional< Pizza > getOnePizza(@PathVariable("id") Integer id) {
        return pizzaRepository.findById(id);
    }

    @GetMapping("/pizzas/search")
    public List< Pizza > findPizzasByIngredients(@RequestParam Map< String, String > requestParams) {
        List list = new ArrayList<Pizza>();
        requestParams.entrySet()
                .forEach(
                        pair -> {
                            if (pair.getValue() != null) {
                                if (pair.getKey().equals("ingredients")) {
                                    list.addAll(pizzaRepository.findAllByIngredientsContaining(pair.getValue()));
                                }
                                if (pair.getKey().equals("diameter")) {
                                    list.addAll(pizzaRepository.findAllByDiameter(Integer.parseInt(pair.getValue())));
                                }
                                if (pair.getKey().equals("type")) {
                                    list.addAll(pizzaRepository.findAllByType(pair.getValue()));
                                }
                                if (pair.getKey().equals("price")) {
                                    list.addAll(pizzaRepository.findAllByPrice(Integer.parseInt(pair.getValue())));
                                }
                            }
                        }
                );
       return list.stream().distinct().toList();
    }

    @PutMapping("/pizzas/{id}")
    public PizzaDto updatePizza(
            @PathVariable(value = "id") Integer id,
            @Valid @RequestBody Pizza pizzaDetails) throws ResourceNotFoundException {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza not found on :: " + id));

        pizza.setDiameter(pizzaDetails.getDiameter());
        pizza.setIngredients(pizzaDetails.getIngredients());
        pizza.setPrice(pizzaDetails.getPrice());
        pizza.setType(pizzaDetails.getType());
        return PizzaMapper.entityToDto(pizzaRepository.save(modelMapper.map(pizza, Pizza.class)));
    }

    @PostMapping("/pizzas")
    PizzaDto saveAPizza(@RequestBody PizzaDto pizzaDto) {
        return PizzaMapper.entityToDto(pizzaRepository.save(modelMapper.map(pizzaDto, Pizza.class)));
    }

    @DeleteMapping("/pizzas/{id}")
    void deleteOnePizza(@PathVariable("id") Integer id) {
        pizzaRepository.deleteById(id);
    }
}
