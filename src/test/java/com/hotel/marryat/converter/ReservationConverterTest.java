package com.hotel.marryat.converter;

import com.hotel.marryat.dto.ReservationDto;
import com.hotel.marryat.entity.Booking;
import com.hotel.marryat.entity.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationConverterTest {

    private ReservationConverter reservationConverter;

    @BeforeEach
    public void init() {
        reservationConverter =  new ReservationConverter();
    }

    @Test
    void convertOneToDto() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(3L);
        Room room = new Room();
        room.setId(1);
        room.setRoomNumber(3);
        Booking booking = Booking.builder().endDate(endDate).startDate(startDate).room(room).build();
        ReservationDto dto = reservationConverter.convertOneToDto(booking);
        assertEquals(startDate, dto.getArrivalDate());
        assertEquals(endDate, dto.getDepartureDate());
        assertEquals(room.getRoomNumber(), dto.getRoomNumber());
    }
}