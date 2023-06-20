package org.flight.dto;

import lombok.*;
import org.flight.enums.PassengerClass;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class ProposedFlight extends SearchCriteria {

    private String plane;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;
    
    private String escale;

    private double price;

    private PassengerClass passengerClass;

}
