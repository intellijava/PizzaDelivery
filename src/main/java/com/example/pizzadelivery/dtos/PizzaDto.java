package com.example.pizzadelivery.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaDto {

    public Integer id;
    public Integer price;
    public Integer diameter;
    public String ingredients;
    public String type;

}
