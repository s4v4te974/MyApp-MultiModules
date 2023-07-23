package dto;

import entity.Plane;
import enums.PassengerClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


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
