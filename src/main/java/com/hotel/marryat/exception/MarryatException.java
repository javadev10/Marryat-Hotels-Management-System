package com.hotel.marryat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MarryatException extends RuntimeException {

    public MarryatException(String message) {
        super(message);
    }
}