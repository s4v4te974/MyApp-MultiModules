package com.businesslogic;

import com.dto.ReservationInformationRecord;
import com.dto.ReservationLoginRecord;
import com.dto.ReservationRecord;
import com.entity.Account;
import com.entity.Reservation;
import com.exception.ReservationException;
import com.mapper.ReservationMapper;
import com.repository.ReservationRepository;
import com.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import static com.utils.ReservationConsts.UNABLE_TO_DELETE_RESERVATION;
import static com.utils.ReservationConsts.UNABLE_TO_RETRIEVE_RESERVATION;
import static com.utils.ReservationConsts.UNABLE_TO_SAVE_RESERVATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationBusinessLogicTest {

    @InjectMocks
    ReservationBusinessLogic reservationBusinessLogic;

    @Mock
    ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    ReservationMapper mapper = Mappers.getMapper(ReservationMapper.class);

    Reservation reservation;

    ReservationRecord reservationRecord;

    ReservationLoginRecord reservationLoginRecord;

    ReservationInformationRecord reservationInformationRecord;

    Account account;

    @BeforeEach
    void setUp(){
        reservation = Reservation.builder() //
                .id(1) //
                .reference("MW8KT") //
                .planeRef("A330-200") //
                .name("name") //
                .lastName("lastName") //
                .price("22.00") //
                .passeport("passeport") //
                .login("login") //
                .passengerClass("ECONOMIC")
                .build(); //

        reservationRecord = new ReservationRecord(
                1, "MW8KT", "name", "lastName", "passeport",
                "A330-200", "22.00", "login", "ECONOMIC");

        reservationLoginRecord = new ReservationLoginRecord( //
                "login", "MW8KT"); //

        reservationInformationRecord = new ReservationInformationRecord(
                "MW8KT", 1, 1, "22.00", "ECONOMIC");

        account = Account.builder() //
                .name("name") //
                .lastName("lastName") //
                .passeport("passeport") //
                .login("login") //
                .build(); //
    }

    @Test
    void retrieveReservationTest() throws ReservationException {

        when(reservationRepository.findByReferenceAndLogin(reservationLoginRecord.reference(),
                reservationLoginRecord.login())).thenReturn(reservation);

        when(mapper.mapToRecord(reservation)).thenReturn(reservationRecord);

        ReservationRecord expected = reservationBusinessLogic.retrieveReservation(reservationLoginRecord);

        assertEquals(1, expected.id());
        assertEquals(reservationLoginRecord.reference(), expected.reference());
        assertEquals(account.getName(), expected.name());
        assertEquals(account.getLastName(), expected.lastName());
        assertEquals(account.getPasseport(), expected.passeport());
        assertEquals(account.getLogin(), expected.login());
        assertEquals(reservation.getPrice(), expected.price());
        assertEquals(reservation.getPlaneRef(), expected.planeRef());
        assertEquals(reservation.getPassengerClass(), expected.passengerClass());
    }

    @Test
    void retrieveReservationException() {
        doThrow(new DataAccessException("Error") {
        }).when(reservationRepository).findByReferenceAndLogin(
                reservationLoginRecord.reference(), reservationLoginRecord.login());
        Exception exception = assertThrows(ReservationException.class,
                () -> reservationBusinessLogic.retrieveReservation(reservationLoginRecord));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(UNABLE_TO_RETRIEVE_RESERVATION));
    }

    @Test
    void createReservationTest() throws ReservationException {
        when(reservationService.createReservation(reservationInformationRecord)).thenReturn(reservationRecord);
        ReservationRecord reservation = reservationBusinessLogic.createReservation(reservationInformationRecord);

        assertEquals(reservationLoginRecord.reference(), reservation.reference());
        assertEquals(reservationInformationRecord.price(), reservation.price());
        assertEquals(reservationInformationRecord.plane(), 1);
        assertEquals(reservationInformationRecord.passengerClass(), reservation.passengerClass());

    }

    @Test
    void createReservationTestException(){
        doThrow(new DataAccessException("Error") {
        }).when(reservationService).createReservation(reservationInformationRecord);

        Exception exception = assertThrows(ReservationException.class,
                () -> reservationBusinessLogic.createReservation(reservationInformationRecord));

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(UNABLE_TO_SAVE_RESERVATION));

    }

    @Test
    void deleteReservationTest() throws ReservationException {
        reservationBusinessLogic.deleteReservation(1);
        verify(reservationRepository, atLeastOnce()).deleteById(1);
    }


    @Test
    void deleteReservationExceptionTest() {
        doThrow(new DataAccessException("Error") {
        }).when(reservationRepository).deleteById(1);

        Exception exception = assertThrows(ReservationException.class,
                () -> reservationBusinessLogic.deleteReservation(1));

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(UNABLE_TO_DELETE_RESERVATION));
    }
}
