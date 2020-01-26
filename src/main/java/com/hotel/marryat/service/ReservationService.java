package com.hotel.marryat.service;

import com.hotel.marryat.dto.NewReservationDto;
import com.hotel.marryat.dto.ReservationDto;
import com.hotel.marryat.dto.ReservationFilterDto;

import java.util.List;

public interface ReservationService {

    List<ReservationDto> getAllReservations(ReservationFilterDto filterDto);
    ReservationDto addNewReservation(NewReservationDto newReservation);
    ReservationDto deleteReservation(Long bookingId);
    ReservationDto updateReservation(ReservationDto reservationDto);
}