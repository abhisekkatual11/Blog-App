package com.myblog.blogApp.exception;

import com.myblog.blogApp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundExcept,
            WebRequest webReq
    ){
        ErrorDetails specificException=new ErrorDetails(new Date(),resourceNotFoundExcept.getMessage(),webReq.getDescription(false));
        return new ResponseEntity<>(specificException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception,
            WebRequest webReq
    ){
        ErrorDetails globalException=new ErrorDetails(new Date(),exception.getMessage(),webReq.getDescription(false));
        return new ResponseEntity<>(globalException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
