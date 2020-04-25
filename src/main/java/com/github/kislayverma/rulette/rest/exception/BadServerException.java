package com.github.kislayverma.rulette.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
public class BadServerException extends RuntimeException {

    public BadServerException(String msg) {
        super(msg);
    }

    public BadServerException(Throwable e) {
        super(e);
    }

    public BadServerException(String msg, Throwable e) {
        super(msg, e);
    }
}
