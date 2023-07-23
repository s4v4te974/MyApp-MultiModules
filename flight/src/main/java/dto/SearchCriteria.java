package dto;

import enums.PassengerClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
