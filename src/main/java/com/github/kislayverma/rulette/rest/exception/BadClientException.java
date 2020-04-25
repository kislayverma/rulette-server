package com.github.kislayverma.rulette.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid request")
public class BadClientException extends RuntimeException {

    public BadClientException(String msg) {
        super(msg);
    }

    public BadClientException(Throwable e) {
        super(e);
    }

    public BadClientException(String msg, Throwable e) {
        super(msg, e);
    }
}
