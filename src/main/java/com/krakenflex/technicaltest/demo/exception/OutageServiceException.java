package com.krakenflex.technicaltest.demo.exception;

import lombok.Data;

@Data
public class OutageServiceException extends RuntimeException{

    public OutageServiceException(String message){
        super(message);
    }
}
