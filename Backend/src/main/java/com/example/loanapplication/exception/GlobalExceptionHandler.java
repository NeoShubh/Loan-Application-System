package com.example.loanapplication.exception;

import com.example.loanapplication.exception.applicant.ApplicantNotFoundException;
import com.example.loanapplication.exception.applicant.PrimaryApplicantaExists;
import com.example.loanapplication.exception.document.DocumentNotFoundException;
import com.example.loanapplication.exception.loanapplication.LoanStageHistoryNotFoundException;
import com.example.loanapplication.exception.user.InvalidCredentialsException;
import com.example.loanapplication.exception.loanapplication.LoanApplicationNotFoundException;
import com.example.loanapplication.exception.user.UserAlreadyExistsException;
import com.example.loanapplication.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.print.Doc;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ApiError apiError = new ApiError("Validation failed", errors);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExistException(UserAlreadyExistsException ex){
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentialsException(InvalidCredentialsException ex){
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex){
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(LoanStageHistoryNotFoundException.class)
    public ResponseEntity<ApiError> HandleloanStageHistoryNotFoundException(LoanStageHistoryNotFoundException ex){
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(ApplicantNotFoundException.class)
    public ResponseEntity<ApiError> HandleApplicantNotFoundException(ApplicantNotFoundException ex){
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND );
    }


        @ExceptionHandler(org.springframework.security.authorization.AuthorizationDeniedException.class)
        public ResponseEntity<?> handleAccessDenied() {
            ApiError apiError = new ApiError("Access Denied: You are not allowed to perform this action");
            return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED );
        }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<?> handleDocumentNotFoundException(DocumentNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ApiError> handleLoanApplicationNotFoundException(LoanApplicationNotFoundException ex){
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND );
    }
    @ExceptionHandler(PrimaryApplicantaExists.class)
    public ResponseEntity<ApiError> handlePrimaryApplicantExists(PrimaryApplicantaExists ex){
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.FOUND );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {

        ApiError apiError = new ApiError("Internal server error");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }


}
