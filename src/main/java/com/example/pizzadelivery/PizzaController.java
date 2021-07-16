package com.example.pizzadelivery;

import com.example.pizzadelivery.entities.Pizza;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PizzaController {

    private final PizzaRepository pizzaRepository;

    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
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
    Pizza saveAPizza(@RequestBody Pizza pizza){
        return pizzaRepository.save(pizza);
    }
}
