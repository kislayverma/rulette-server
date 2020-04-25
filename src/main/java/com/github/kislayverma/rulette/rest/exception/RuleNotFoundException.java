package com.github.kislayverma.rulette.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No rule found for the given input")
public class RuleNotFoundException extends RuntimeException {

    public RuleNotFoundException(String msg) {
        super(msg);
    }

    public RuleNotFoundException(Throwable e) {
        super(e);
    }

    public RuleNotFoundException(String msg, Throwable e) {
        super(msg, e);
    }
}
