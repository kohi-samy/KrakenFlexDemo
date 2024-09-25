package com.krakenflex.technicaltest.demo.handler;

import com.krakenflex.technicaltest.demo.dto.exception.KFCommonExceptionResponse;
import com.krakenflex.technicaltest.demo.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class KFClientRestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static String EXCEPTION_KEY ="Exception message : {} , Exception stack trace : {}" ;

    @ExceptionHandler(value = OutageServiceException.class)
    protected ResponseEntity<Object> handleOutageServiceException(OutageServiceException ex, HttpServletRequest request){

        KFCommonExceptionResponse kfCommonExceptionResponse = new KFCommonExceptionResponse(
                HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
        log.error(EXCEPTION_KEY, ex.getMessage(), ex);
        return  new ResponseEntity<>(kfCommonExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AuthorizationException.class)
    protected ResponseEntity<Object> handleAuthorizationException(AuthorizationException ex, HttpServletRequest request){

        KFCommonExceptionResponse kfCommonExceptionResponse = new KFCommonExceptionResponse(
                HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI());
        log.error(EXCEPTION_KEY, ex.getMessage(), ex);
        return  new ResponseEntity<>(kfCommonExceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = RequestLimitException.class)
    protected ResponseEntity<Object> handleRequestLimitException(RequestLimitException ex, HttpServletRequest request){

        KFCommonExceptionResponse kfCommonExceptionResponse = new KFCommonExceptionResponse(
                HttpStatus.TOO_MANY_REQUESTS, ex.getMessage(), request.getRequestURI());
        log.error(EXCEPTION_KEY, ex.getMessage(), ex);
        return  new ResponseEntity<>(kfCommonExceptionResponse, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(value = BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, HttpServletRequest request){

        KFCommonExceptionResponse kfCommonExceptionResponse = new KFCommonExceptionResponse(
                HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
        log.error(EXCEPTION_KEY, ex.getMessage(), ex);
        return  new ResponseEntity<>(kfCommonExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, HttpServletRequest request){

        KFCommonExceptionResponse kfCommonExceptionResponse = new KFCommonExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI());
        log.error(EXCEPTION_KEY, ex.getMessage(), ex);
        return  new ResponseEntity<>(kfCommonExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = OutageServiceRequestValidationException.class)
    protected ResponseEntity<Object> handleOutageServiceRequestValidationException(OutageServiceRequestValidationException ex, HttpServletRequest request){

        KFCommonExceptionResponse kfCommonExceptionResponse = new KFCommonExceptionResponse(
                HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
        log.error(EXCEPTION_KEY, ex.getMessage(), ex);
        return  new ResponseEntity<>(kfCommonExceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
