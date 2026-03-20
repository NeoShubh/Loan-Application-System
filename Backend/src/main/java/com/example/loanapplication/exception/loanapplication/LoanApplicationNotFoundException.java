package com.example.loanapplication.exception.loanapplication;

public class LoanApplicationNotFoundException extends RuntimeException{
    public LoanApplicationNotFoundException(String msg){
        super(msg);
    }
}
