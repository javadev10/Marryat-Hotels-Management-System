package com.hotel.marryat.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationDto {
    @NotNull
    private Long reservationId;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private Integer roomNumber;
}