package com.backend.exceptions;

public class JSONParsingException extends RuntimeException{

    public JSONParsingException(String message) {
        super(message);
    }
}
