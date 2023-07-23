package com.service.impl;

import com.dto.ReservationDto;
import com.entity.Account;
import com.entity.Plane;
import com.entity.Reservation;
import com.mapper.MapToReservation;
import com.repository.AccountRepository;
import com.repository.PlaneRepository;
import com.repository.ReservationRepository;
import com.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final PlaneRepository planeRepository;

    private final AccountRepository accountRepository;

    private final MapToReservation mapToReservation;

    @Override
    public Reservation createReservation(ReservationDto dto) {
        boolean isReferenceOk = false;
        String reference = null;
        Reservation reservation = null;

        List<String> references = reservationRepository.findAll()
                .stream().map(Reservation::getReference).toList();
        Account account = accountRepository.findById(dto.getUser()).orElse(null);
        Plane plane = planeRepository.findById(dto.getPlane()).orElse(null);

        while(!isReferenceOk){
            reference = generateReferences();
            if(!references.contains(reference)){
                isReferenceOk = true;
            }
        }

        if(plane != null || account != null){
            reservation = mapToReservation.mapToReservation(plane, account, dto);
        }

        if(reservation != null){
            reservationRepository.save(reservation);
        }
        return reservation;
    }

    public String generateReferences(){
        return RandomStringUtils.randomAlphabetic(6);
    }
}
