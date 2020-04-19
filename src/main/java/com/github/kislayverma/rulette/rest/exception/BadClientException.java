package com.github.kislayverma.rulette.rest.exception;

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
