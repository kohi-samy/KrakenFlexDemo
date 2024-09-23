package com.krakenflex.technicaltest.demo.exception;

import lombok.Data;

@Data
public class AuthorizationException extends  RuntimeException{

    public AuthorizationException(String message){
        super(message);
    }
}
