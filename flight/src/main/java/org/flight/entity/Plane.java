package org.flight.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "plane")
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "company")
    private String company;

    @Column(name = "model")
    private String model;

    @Column(name = "range")
    private double range;

    @Column(name = "isAvailable")
    private boolean isAvailable;

    @Column(name = "consommation")
    private double conso;

}