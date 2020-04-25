package com.github.kislayverma.rulette.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such rule system found")
public class RuleSystemNotFoundException extends RuntimeException {

    public RuleSystemNotFoundException(String msg) {
        super(msg);
    }

    public RuleSystemNotFoundException(Throwable e) {
        super(e);
    }

    public RuleSystemNotFoundException(String msg, Throwable e) {
        super(msg, e);
    }
}
