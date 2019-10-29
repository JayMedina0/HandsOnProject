package com.trilogyed.bookservice.controller;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<VndErrors.VndError> handleClientErrorException(ClientErrorException e, WebRequest req) {
        return new ResponseEntity<>(
                new VndErrors.VndError(req.toString(), e.getMessage()),
                e.getStatus()
        );
    }
}
