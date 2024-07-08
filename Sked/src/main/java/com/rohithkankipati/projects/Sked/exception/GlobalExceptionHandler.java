package com.rohithkankipati.projects.Sked.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.rohithkankipati.projects.Sked.util.MessageUtil;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(SkedException.class)
    public ResponseEntity<ErrorResponse> handleRoomMateException(SkedException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getStatus(), 
            MessageUtil.getMessage(ex.getErrorCode())
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

}
