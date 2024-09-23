package com.krakenflex.technicaltest.demo.dto.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;
import com.google.gson.annotations.JsonAdapter;

import java.time.LocalDateTime;

@Data
public class KFCommonExceptionResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonAdapter(LocalDateTime.class)
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private String message;
    private String path;

    private KFCommonExceptionResponse(){
        timeStamp = LocalDateTime.now();
    }

    public KFCommonExceptionResponse(HttpStatus status){
        this();
        this.status = status;
    }

    public KFCommonExceptionResponse(HttpStatus status, String message, String path){
        this();
        this.status = status;
        this.message = message;
        this.path = path;
    }


}
