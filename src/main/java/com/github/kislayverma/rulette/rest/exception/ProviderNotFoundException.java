package com.github.kislayverma.rulette.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No data provider for the given input")
public class ProviderNotFoundException extends RuntimeException {

    public ProviderNotFoundException(String msg) {
        super(msg);
    }

    public ProviderNotFoundException(Throwable e) {
        super(e);
    }

    public ProviderNotFoundException(String msg, Throwable e) {
        super(msg, e);
    }
}
