package org.flight.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cities")
public class Cities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "ue")
    private boolean isUe;

    @Column(name = "xCoordonates")
    private double xCoordonates;

    @Column(name = "yCoordonates")
    private double yCoordonates;













}