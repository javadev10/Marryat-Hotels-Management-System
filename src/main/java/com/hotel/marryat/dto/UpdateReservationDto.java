package com.hotel.marryat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class UpdateReservationDto {

    @NotNull
    private LocalDate arrivalDate;
    @NotNull
    private LocalDate departureDate;
    @NotNull
    private Integer roomId;
}
