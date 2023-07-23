package com.businesslogic;

import com.dto.ReservationDto;
import com.dto.RetrieveReservationDto;
import com.entity.Account;
import com.entity.Reservation;
import com.exception.ReservationException;
import com.repository.ReservationRepository;
import com.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import static com.utils.ReservationConsts.UNABLE_TO_RETRIEVE_RESERVATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationBusinessLogicTest {

    @InjectMocks
    ReservationBusinessLogic reservationBusinessLogic;

    @Mock
    ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepository;

    Reservation reservation;

    ReservationDto reservationDto;

    RetrieveReservationDto retrieveDto;

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

        reservationDto = ReservationDto.builder() //
                .user(1) //
                .ref("MW8KT") //
                .plane(1) //
                .passengerClass("ECONOMIC") //
                .price("22.0") //
                .build(); //

        retrieveDto = RetrieveReservationDto.builder() //
                .login("login") //
                .reference("MW8KT") //
                .build(); //

        account = Account.builder() //
                .name("name") //
                .lastName("lastName") //
                .passeport("passeport") //
                .login("login") //
                .build(); //
    }

    @Test
    void retrieveReservationTest() throws ReservationException {

        when(reservationRepository.retrieveReservationByReference(retrieveDto.getReference(), retrieveDto.getLogin()))
                .thenReturn(reservation);

        Reservation expected = reservationBusinessLogic.retrieveReservation(retrieveDto);

        assertEquals(1, expected.getId());
        assertEquals(reservationDto.getRef(), expected.getReference());
        assertEquals(account.getName(), expected.getName());
        assertEquals(account.getLastName(), expected.getLastName());
        assertEquals(account.getPasseport(), expected.getPasseport());
        assertEquals(account.getLogin(), expected.getLogin());
        assertEquals("22.00", expected.getPrice());
        assertEquals("A330-200", expected.getPlaneRef());
        assertEquals("ECONOMIC", expected.getPassengerClass());

    }

    @Test
    void retrieveReservationException() {
        doThrow(new DataAccessException("Error") {
        }).when(reservationRepository).retrieveReservationByReference(retrieveDto.getReference(), retrieveDto.getLogin());
        Exception exception = assertThrows(ReservationException.class,
                () -> reservationBusinessLogic.retrieveReservation(retrieveDto));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(UNABLE_TO_RETRIEVE_RESERVATION));
    }

    @Test
    void createReservationTest(){

    }

    @Test
    void deleteReservationTest(){

    }


}
