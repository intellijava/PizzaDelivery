package com.example.pizzadelivery.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaDto {

    public Integer id;
    public String diameter;
    public String sauce;
    public String type;

}
