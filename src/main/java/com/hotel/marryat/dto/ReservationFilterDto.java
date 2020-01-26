package com.hotel.marryat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ReservationFilterDto {

    @NotNull
    private LocalDate startDateEquals;
    @NotNull
    private LocalDate endDateEquals;
}