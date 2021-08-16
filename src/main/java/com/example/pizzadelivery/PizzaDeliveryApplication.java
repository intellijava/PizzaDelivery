package com.example.pizzadelivery;

import com.example.pizzadelivery.entities.Pizza;
import com.example.pizzadelivery.repositories.PizzaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class PizzaDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaDeliveryApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(PizzaRepository pizzaRepository){

        return (args) -> {
            if (pizzaRepository.count() == 0){
            pizzaRepository.save(new Pizza(0,90, 8,"ost,tomatsås","Veggie"));
            }
        };
    }

}
