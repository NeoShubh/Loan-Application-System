package com.example.loanapplication.exception.rcuCase;

public class RCUStatusCanNotBeChangedException extends RuntimeException {
    public RCUStatusCanNotBeChangedException(String message) {
        super(message);
    }
}
