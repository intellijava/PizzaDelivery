package com.example.pizzadelivery.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pizza")
public class Pizza implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "DIAMETER")
    private String DIAMETER;

    @Column(name = "SAUCE")
    private String SAUCE;

    @Column(name = "TYPE")
    private String TYPE;

}
