package com.hotel.marryat.converter;

import com.hotel.marryat.dto.NewReservationDto;
import com.hotel.marryat.dto.ReservationDto;
import com.hotel.marryat.entity.Booking;
import com.hotel.marryat.entity.Room;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReservationConverter {

    public ReservationDto convertOneToDto(Booking booking) {
        return Optional.ofNullable(booking).map(b->
                ReservationDto.builder()
                        .reservationId(booking.getId())
                        .arrivalDate(booking.getStartDate())
                        .departureDate(booking.getEndDate())
                        .roomNumber(booking.getRoom().getRoomNumber())
                        .build()
                ).orElse(null);
    }

    public List<ReservationDto> convertAllToDto(List<Booking> bookings) {
        return Optional.ofNullable(bookings).orElse(Collections.emptyList())
                .stream()
                .map(b->convertOneToDto(b))
                .collect(Collectors.toList());
    }

    public Booking convertOneNewDtoToEntity(NewReservationDto newReservationDto) {
        return Optional.ofNullable(newReservationDto).map(b->
                Booking.builder()
                        .startDate(newReservationDto.getArrivalDate())
                        .endDate(newReservationDto.getDepartureDate())
                        .room(new Room())
                        .build()
        ).orElse(null);
    }
}