package com.reservation.dto;

public record ReservationRecord(
        int id, //
        String reference, //
        String name, //
        String lastName, //
        String passeport, //
        String planeRef, //
        String price, //
        String login, //
        String passengerClass) {
}
