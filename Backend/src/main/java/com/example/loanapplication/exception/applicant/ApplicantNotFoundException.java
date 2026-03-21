package com.example.loanapplication.exception.applicant;

public class ApplicantNotFoundException extends RuntimeException{
    public  ApplicantNotFoundException(String msg){
        super(msg);
    }
}
