package com.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name= "reference")
    private String reference;

    @Column(name = "name")
    private String name;

    @Column(name= "lastName")
    private String lastname;

    @Column(name = "passeport")
    private String passeport;

    @Column(name = "planeref")
    private String planeRef;

    @Column(name = "price")
    private double price;

    @Column(name = "login")
    private String login;

    @Column(name= "class")
    private String passengerClass;

}
