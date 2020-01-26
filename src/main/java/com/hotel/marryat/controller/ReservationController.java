package com.hotel.marryat.controller;

import com.hotel.marryat.dto.NewReservationDto;
import com.hotel.marryat.dto.ReservationDto;
import com.hotel.marryat.dto.ReservationFilterDto;
import com.hotel.marryat.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/management/reservation")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping(value = "list")
    public List<ReservationDto> getAllReservations(@Valid @RequestBody ReservationFilterDto reservationFilterDto) {
        log.info("Request to get reservations by filter {}", reservationFilterDto);
        return reservationService.getAllReservations(reservationFilterDto);
    }

    @PostMapping
    public ReservationDto addNewReservation(@Valid @RequestBody NewReservationDto newReservation) {
        log.info("Request to add new reservation {}", newReservation);
        return reservationService.addNewReservation(newReservation);
    }

    @DeleteMapping
    public ReservationDto deleteReservations(@RequestParam Long reservationId) {
        log.info("Request to delete reservation {}", reservationId);
        return reservationService.deleteReservation(reservationId);
    }

    @PutMapping
    public ReservationDto updateReservation(@RequestBody ReservationDto reservationDto) {
        log.info("Request to update reservation {}", reservationDto);
        return reservationService.updateReservation(reservationDto);
    }
}