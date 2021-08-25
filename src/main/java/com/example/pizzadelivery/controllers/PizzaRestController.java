package com.example.pizzadelivery.controllers;

import com.example.pizzadelivery.dtos.PizzaDto;
import com.example.pizzadelivery.entities.Pizza;
import com.example.pizzadelivery.mapper.PizzaMapper;
import com.example.pizzadelivery.repositories.PizzaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PizzaRestController {

    private ObjectMapper objectMapper = new ObjectMapper();
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
    public List< Pizza > getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @GetMapping("/pizzas/{id}")
    Optional< Pizza > getOnePizza(@PathVariable("id") Integer id) {
        return Optional.ofNullable(pizzaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/pizzas/search")
    public List< Pizza > findPizzasByRequestParams(@RequestParam Map< String, String > requestParams) {
        List list = new ArrayList< Pizza >();
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
    public ResponseEntity< HttpStatus > updatePizza(
            @PathVariable(value = "id") Integer id,
            @Valid @RequestBody Pizza pizzaDetails) throws ResourceNotFoundException {
        try {
            Pizza pizza = pizzaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Pizza not found on :: " + id));

            pizza.setDiameter(pizzaDetails.getDiameter());
            pizza.setIngredients(pizzaDetails.getIngredients());
            pizza.setPrice(pizzaDetails.getPrice());
            pizza.setType(pizzaDetails.getType());
            PizzaMapper.entityToDto(pizzaRepository.save(modelMapper.map(pizza, Pizza.class)));
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping(path = "/pizzas/{id}", consumes = "application/json-patch+json")
    public ResponseEntity< Pizza > updatePizza(@PathVariable Integer id, @RequestBody JsonPatch jsonPatch) {
        try {
            Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pizza not found on :: " + id));
            Pizza pizzaPatched = applyPatchToPizza(jsonPatch, pizza);
            updatePizza(pizzaPatched);
            return ResponseEntity.ok(pizzaPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public void updatePizza(Pizza pizza) {
        var pizzaDto = PizzaMapper.entityToDto(pizza);
        PizzaMapper.entityToDto(pizzaRepository.save(modelMapper.map(pizzaDto, Pizza.class)));
    }

    private Pizza applyPatchToPizza(
            JsonPatch patch, Pizza targetPizza) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetPizza, JsonNode.class));
        return objectMapper.treeToValue(patched, Pizza.class);
    }

    @PostMapping("/pizzas")
    public PizzaDto saveAPizza(@RequestBody PizzaDto pizzaDto) {
        return PizzaMapper.entityToDto(pizzaRepository.save(modelMapper.map(pizzaDto, Pizza.class)));
    }

    @DeleteMapping("/pizzas/{id}")
    public void deletePizza(@PathVariable("id") Integer id) {

        if (pizzaRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else
            try {
                pizzaRepository.deleteById(id);
            } catch (ResponseStatusException e) {
                e.printStackTrace();
            }
    }
}
