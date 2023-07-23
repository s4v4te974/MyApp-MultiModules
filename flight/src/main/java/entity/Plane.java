package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Builder
@Table(name = "plane")
@NoArgsConstructor
@AllArgsConstructor
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "builder")
    private String builder;

    @Column(name = "model")
    private String model;

    @Column(name = "range")
    private double range;

    @Column(name = "isAvailable")
    private boolean isAvailable;

    @Column(name = "consommation")
    private double conso;

    @Column(name = "vitesse_moyenne")
    private double speed;

}