package com.example.pizzadelivery.repositories;

import com.example.pizzadelivery.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository extends JpaRepository< Customer, Integer >, JpaSpecificationExecutor< Customer > {

}