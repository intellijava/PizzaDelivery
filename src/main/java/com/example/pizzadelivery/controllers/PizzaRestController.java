package com.example.pizzadelivery.controllers;

import com.example.pizzadelivery.dtos.PizzaDto;
import com.example.pizzadelivery.entities.Pizza;
import com.example.pizzadelivery.mapper.PizzaMapper;
import org.assertj.core.util.Preconditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;
import com.example.pizzadelivery.repositories.PizzaRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class PizzaRestController {

    private final PizzaRepository pizzaRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public PizzaRestController(PizzaRepository pizzaRepository, ModelMapper modelMapper) {
        this.pizzaRepository = pizzaRepository;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/pizzas")
    List< Pizza > getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @GetMapping("/pizzas/{id}")
    Optional< Pizza > getOnePizza(@PathVariable("id") Integer id) {
        return pizzaRepository.findById(id);
    }

    //    @GetMapping("/search/{name}")
//    Pizza findAPizza(String name){
//        return pizzaRepository.findByName(name);
//    }
//    @PutMapping("/pizzas/{id}")
//    public Optional< Pizza > updateUser(
//            @PathVariable(value = "id") Long id,
//            @Valid @RequestBody Pizza userDetails) throws ResourceNotFoundException {
//        Pizza user = pizzaRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));
//
//        user.setEmailId(userDetails.getEmailId());
//        user.setLastName(userDetails.getLastName());
//        user.setFirstName(userDetails.getFirstName());
//        user.setUpdatedAt(new Date());
//        final User updatedUser = userRepository.save(user);
//        return ResponseEntity.ok(updatedUser);
//    }

    @PostMapping("/pizzas")
    PizzaDto saveAPizza(@RequestBody PizzaDto pizzaDto) {
        return PizzaMapper.entityToDto(pizzaRepository.save(modelMapper.map(pizzaDto, Pizza.class)));
    }


    @DeleteMapping("/pizzas/{id}")
    void deleteOnePizza(@PathVariable("id") Integer id) {
        pizzaRepository.deleteById(id);
    }
}
