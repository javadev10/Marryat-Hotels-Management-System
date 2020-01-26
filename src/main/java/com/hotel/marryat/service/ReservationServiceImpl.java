package com.hotel.marryat.service;

import com.hotel.marryat.converter.ReservationConverter;
import com.hotel.marryat.dto.NewReservationDto;
import com.hotel.marryat.dto.ReservationDto;
import com.hotel.marryat.dto.ReservationFilterDto;
import com.hotel.marryat.dto.UpdateReservationDto;
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
import java.util.stream.Collectors;

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
        List<Booking> existingBookings = bookingRepository.findByRoomWithinDateRange(newReservation.getRoomId(), newReservation.getArrivalDate(), newReservation.getDepartureDate());
        if(!existingBookings.isEmpty()) {
            throw new MarryatException("Already booked by " + existingBookings);
        }
        Booking booking = reservationConverter.convertOneNewDtoToEntity(newReservation);
        booking.setRoom(getRoom(newReservation.getRoomId()));
        bookingRepository.save(booking);
        log.info("Added new reservation {}", booking);
        return reservationConverter.convertOneToDto(booking);
    }

    @Override
    public ReservationDto deleteReservation(Long bookingId) {
        Booking booking = getBooking(bookingId);
        log.info("Delete booking {}", bookingId);
        ReservationDto deletedReservation = reservationConverter.convertOneToDto(booking);
        booking.setRoom(null);
        bookingRepository.delete(booking);
        return deletedReservation;
    }

    @Override
    public ReservationDto updateReservation(Long bookingId, UpdateReservationDto reservationDto) {
        Booking booking = getBooking(bookingId);
        List<Booking> foundBookings = bookingRepository.findByRoomWithinDateRange(reservationDto.getRoomId(), reservationDto.getArrivalDate(), reservationDto.getDepartureDate());
        List<Booking> existingBookings = foundBookings.stream().filter(b-> !b.getId().equals(bookingId)).collect(Collectors.toList());
        if(!existingBookings.isEmpty()) {
            throw new MarryatException("Already booked by " + existingBookings);
        }
        booking.setRoom(getRoom(reservationDto.getRoomId()));
        booking.setStartDate(reservationDto.getArrivalDate());
        booking.setEndDate(reservationDto.getDepartureDate());
        log.info("Update reservation {}", booking);
        return reservationConverter.convertOneToDto(booking);
    }

    private Room getRoom(Integer roomId) {
       return roomRepository.findById(roomId).orElseThrow(()->new MarryatException("No room found with id " + roomId));
    }

    private Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(()->new MarryatException("No booking found with id " + bookingId));
    }
}