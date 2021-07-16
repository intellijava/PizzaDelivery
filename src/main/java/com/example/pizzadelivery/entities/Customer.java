package com.example.pizzadelivery.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "EMAIL")
    private String EMAIL;

    @Column(name = "NAME")
    private String NAME;

    @Column(name = "PHONE")
    private String PHONE;

}
