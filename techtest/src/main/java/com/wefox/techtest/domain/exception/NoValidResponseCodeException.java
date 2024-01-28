package com.wefox.techtest.domain.exception;

public class NoValidResponseCodeException extends Exception {

    public NoValidResponseCodeException(final int statusCode) {
        super("No valid response code: " + statusCode);
    }
}
