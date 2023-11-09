package com.reservation.dto;

public record ReservationInformationRecord(
        String ref, int user, int plane, String price, String passengerClass) {}
