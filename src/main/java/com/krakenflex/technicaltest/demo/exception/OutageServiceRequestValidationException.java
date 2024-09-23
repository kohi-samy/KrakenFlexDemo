package com.krakenflex.technicaltest.demo.exception;

import lombok.Data;

@Data
public class OutageServiceRequestValidationException extends RuntimeException{

    String message;

    public OutageServiceRequestValidationException(String message){
        super(message);
        this.message = message;
    }
}
