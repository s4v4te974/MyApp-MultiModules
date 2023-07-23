package org.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.flight.enums.PassengerClass;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SearchCriteria {

    private int idDeparture;

    private int idArrival;

    private LocalDateTime date;

    private PassengerClass passengerClass;

}
