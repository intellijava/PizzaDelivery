package com.example.pizzadelivery.repositories;

import com.example.pizzadelivery.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository< Employee, Integer >, JpaSpecificationExecutor< Employee > {

}