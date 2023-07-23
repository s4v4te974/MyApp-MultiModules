package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private String ref;

    private int user;

    private int plane;

    private String price;

    private String passengerClass;

}
