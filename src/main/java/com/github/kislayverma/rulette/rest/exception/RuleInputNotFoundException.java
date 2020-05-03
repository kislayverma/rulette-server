package com.github.kislayverma.rulette.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No rule input found for the given input")
public class RuleInputNotFoundException extends RuntimeException {

    public RuleInputNotFoundException(String msg) {
        super(msg);
    }

    public RuleInputNotFoundException(Throwable e) {
        super(e);
    }

    public RuleInputNotFoundException(String msg, Throwable e) {
        super(msg, e);
    }
}
