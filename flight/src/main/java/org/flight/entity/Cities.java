package org.flight.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "cities")
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "lattitude")
    private double lattitude;

    @Column(name = "longitude")
    private double longitude;













}