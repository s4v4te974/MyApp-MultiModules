package com.enums;

import com.flight.enums.PassengerClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PassengerClassTest {

    @Test
    void passengerEconomics() {
        assertEquals("ECONOMIC", PassengerClass.ECONOMIC.name());
    }

    @Test
    void passengerAffair() {
        assertEquals("AFFAIR", PassengerClass.AFFAIR.name());
    }

    @Test
    void passengerFirstClass() {
        assertEquals("FIRSTCLASS", PassengerClass.FIRSTCLASS.name());
    }
}
