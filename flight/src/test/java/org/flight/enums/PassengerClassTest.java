package org.flight.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PassengerClassTest {

    @Test
    void passengerEconomics(){
        assertEquals(PassengerClass.ECONOMIC.name(), "ECONOMIC");
    }

    @Test
    void passengerAffair(){
        assertEquals(PassengerClass.AFFAIR.name(), "AFFAIR");
    }

    @Test
    void passengerFirstClass(){
        assertEquals(PassengerClass.FIRSTCLASS.name(), "FIRSTCLASS");
    }
}
