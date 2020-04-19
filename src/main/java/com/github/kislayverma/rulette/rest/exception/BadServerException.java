package com.github.kislayverma.rulette.rest.exception;

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
