package com.hotel.marryat.service;

import com.hotel.marryat.converter.ReservationConverter;
import com.hotel.marryat.dto.NewReservationDto;
import com.hotel.marryat.dto.ReservationDto;
import com.hotel.marryat.dto.ReservationFilterDto;
import com.hotel.marryat.entity.Booking;
import com.hotel.marryat.entity.Room;
import com.hotel.marryat.exception.MarryatException;
import com.hotel.marryat.repository.BookingRepository;
import com.hotel.marryat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final ReservationConverter reservationConverter;

    @Override
    public List<ReservationDto> getAllReservations(ReservationFilterDto filterDto) {
        List<Booking> bookings = bookingRepository.findByStartDateAndEndDate(filterDto.getStartDateEquals(), filterDto.getEndDateEquals());
        return reservationConverter.convertAllToDto(bookings);
    }

    @Override
    public ReservationDto addNewReservation(NewReservationDto newReservation) {
        Optional<Booking> existingBooking = bookingRepository.findByRoomWithinDateRange(newReservation.getRoomId(), newReservation.getArrivalDate(), newReservation.getDepartureDate());
        if(existingBooking.isPresent()) {
            throw new MarryatException("Already booked from " + newReservation.getArrivalDate() + " till " + newReservation.getDepartureDate());
        }
        Booking booking = reservationConverter.convertOneNewDtoToEntity(newReservation);
        Room room = roomRepository.findById(newReservation.getRoomId())
                .orElseThrow(()->new MarryatException("No room found with id " + newReservation.getRoomId()));
        booking.setRoom(room);
        bookingRepository.save(booking);
        log.info("Added new reservation {}", booking);
        return reservationConverter.convertOneToDto(booking);
    }

    @Override
    public ReservationDto deleteReservation(Long reservationId) {
        Booking booking = bookingRepository.findById(reservationId).orElseThrow(()->new MarryatException("No reservation found with id " + reservationId));
        log.info("Delete booking {}", reservationId);
        ReservationDto deletedReservation = reservationConverter.convertOneToDto(booking);
        booking.setRoom(null);
        bookingRepository.delete(booking);
        return deletedReservation;
    }

    @Override
    public ReservationDto updateReservation(ReservationDto reservationDto) {
        Long bookingId = reservationDto.getReservationId();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->new MarryatException("No reservation found with id " + bookingId));
        if(reservationDto.getArrivalDate() != null) booking.setStartDate(reservationDto.getArrivalDate());
        if(reservationDto.getDepartureDate() != null) booking.setEndDate(reservationDto.getDepartureDate());
        log.info("Update reservation {}", booking);
        return reservationConverter.convertOneToDto(booking);
    }
}