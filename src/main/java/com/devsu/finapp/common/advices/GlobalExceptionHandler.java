package com.devsu.finapp.common.advices;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsu.finapp.common.exceptions.ResourceNotFoundException;
import com.devsu.finapp.common.pojos.MessageError;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageError> manejarMiExcepcion(ResourceNotFoundException ex) {
        MessageError error = new MessageError();
        error.setMessage(ex.getMessage());
        error.setCode(getCode());
        log.error(error, ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    public String getCode() {
        return UUID.randomUUID().toString();
    }

}
