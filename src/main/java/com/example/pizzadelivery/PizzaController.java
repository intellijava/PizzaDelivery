package com.example.pizzadelivery;

import com.example.pizzadelivery.dtos.PizzaDto;
import com.example.pizzadelivery.entities.Pizza;
import com.example.pizzadelivery.mapper.PizzaMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PizzaController {

    private final PizzaRepository pizzaRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public PizzaController(PizzaRepository pizzaRepository, ModelMapper modelMapper) {
        this.pizzaRepository = pizzaRepository;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/pizzas")
    List< Pizza > getAllPizzas(){
        return pizzaRepository.findAll();
    }

    @GetMapping("/pizzas/{id}")
    Optional<Pizza> getOnePizza(@PathVariable("id")Integer id){
        return pizzaRepository.findById(id);
    }

    @PostMapping("/pizzas")
    PizzaDto saveAPizza(@RequestBody PizzaDto pizzaDto){
        return PizzaMapper.entityToDto(pizzaRepository.save(modelMapper.map(pizzaDto, Pizza.class)));
    }
}
