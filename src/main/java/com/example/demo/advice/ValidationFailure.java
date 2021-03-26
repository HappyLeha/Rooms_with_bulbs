package com.example.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class ValidationFailure {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String validationFailureHandler (MethodArgumentNotValidException ex) {
        String resultMessage="";

        for (ObjectError error: ex.getAllErrors()) {
            resultMessage += error.getDefaultMessage()+"\n";
        }
        return resultMessage;
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String validationFailureHandler (IllegalArgumentException ex) {
        return "Invalid country";
    }

    @ResponseBody
    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String validationFailureHandler (HttpServerErrorException ex) {
        return "Invalid ip";
    }
}
