package com.github.kislayverma.rulette.rest.exception;

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
