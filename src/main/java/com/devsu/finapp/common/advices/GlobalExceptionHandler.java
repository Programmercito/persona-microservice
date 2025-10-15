package com.devsu.finapp.common.advices;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<MessageError> manejarMiExcepcion(SQLIntegrityConstraintViolationException ex) {
        MessageError error = new MessageError();
        String message = ex.getMessage();
        HttpStatus status = null;
        if (message.contains("UK_")) {
            String value = extractDuplicateValueForUKPrefix(message);
            if (value != null) {
                error.setMessage("The value '" + value + "' already exists in the system.");
            } else {
                error.setMessage("A unique constraint violation occurred.");
            }
            status = HttpStatus.CONFLICT;
        } else {
            error.setMessage("A database error occurred.");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        error.setCode(getCode());
        log.error(error, ex);
        return ResponseEntity
                .status(status)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageError> manejarMiExcepcion(Exception ex) {
        MessageError error = new MessageError();
        error.setMessage("An unexpected error occurred. Please try again later.");
        error.setCode(getCode());
        log.error(error, ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    public static String extractDuplicateValueForUKPrefix(String errorMessage) {
        String regex = "Duplicate entry '([^']+)' for key 'UK_[^']+'";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public String getCode() {
        return UUID.randomUUID().toString();
    }

}
