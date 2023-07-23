package org.flight.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.flight.entity.Plane;
import org.flight.enums.PassengerClass;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProposedFlight extends SearchCriteria {

    private Plane plane;

    private double price;

    private PassengerClass passengerClass;

}
