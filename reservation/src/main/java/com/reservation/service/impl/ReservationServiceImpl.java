package com.reservation.service.impl;

import com.reservation.dto.ReservationInformationRecord;
import com.reservation.dto.ReservationRecord;
import com.account.entity.Account;
import com.flight.entity.Plane;
import com.reservation.entity.Reservation;
import com.reservation.mapper.ReservationMapper;
import com.account.repository.AccountRepository;
import com.flight.repository.PlaneRepository;
import com.reservation.repository.ReservationRepository;
import com.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final PlaneRepository planeRepository;

    private final AccountRepository accountRepository;

    private final ReservationMapper reservationMapper;

    @Override
    public ReservationRecord createReservation(ReservationInformationRecord reservationInformationRecord) {
        boolean isReferenceOk = false;
        ReservationRecord reservation = null;

        log.info("Retrieve informations for build reservation");
        List<String> references = reservationRepository.findAll()
                .stream().map(Reservation::getReference).toList();
        Account account = accountRepository.findById(reservationInformationRecord.user()).orElse(null);
        Plane plane = planeRepository.findById(reservationInformationRecord.plane()).orElse(null);

        while(!isReferenceOk){
            String reference = generateReferences();
            if(!references.contains(reference)){
                isReferenceOk = true;
            }
        }

        if(plane != null || account != null){
            reservation = reservationMapper.mapToRecordFromMultipleSource(plane, account, reservationInformationRecord);
            if(reservation != null){
                reservationRepository.save(reservationMapper.mapToEntity(reservation));
            }
        }
        log.info("Return the created reservation");
        return reservation;
    }

    @SuppressWarnings("squid:SCS")
    public String generateReferences(){
        return RandomStringUtils.randomAlphabetic(6);
    }
}
