package com.trilogyed.bookservice.controller;

import org.springframework.http.HttpStatus;

public class ClientErrorException extends Exception {
    private HttpStatus status;
    ClientErrorException(HttpStatus status, String mesage) {
        super(mesage);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
