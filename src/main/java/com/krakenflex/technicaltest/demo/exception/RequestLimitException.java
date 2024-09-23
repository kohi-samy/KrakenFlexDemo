package com.krakenflex.technicaltest.demo.exception;

import lombok.Data;

@Data
public class RequestLimitException extends  RuntimeException{

    public RequestLimitException(String message){
        super(message);
    }
}
