package com.example.pizzadelivery.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "salesorder")
public class Salesorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "ORDERDATE")
    private String ORDERDATE;

    @Column(name = "ORDERTIME")
    private String ORDERTIME;

}
