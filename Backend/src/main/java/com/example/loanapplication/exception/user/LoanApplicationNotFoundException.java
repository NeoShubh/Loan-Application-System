package com.example.loanapplication.exception.user;

public class LoanApplicationNotFoundException extends RuntimeException{
    public LoanApplicationNotFoundException(String msg){
        super(msg);
    }
}
