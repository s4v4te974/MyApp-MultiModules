package com.reservation.repository;

import com.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    
    Reservation findByReferenceAndLogin(String ref, String login);

}