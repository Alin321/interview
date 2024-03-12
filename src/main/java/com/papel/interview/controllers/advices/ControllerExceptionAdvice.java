package com.papel.interview.controllers.advices;

import com.papel.interview.exceptions.EntityAlreadyModifiedException;
import com.papel.interview.exceptions.ErrorCode;
import com.papel.interview.exceptions.InterviewBaseException;
import com.papel.interview.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(ObjectNotFoundException.class)
    protected ResponseEntity<Map<String, String>> handleObjectNotFoundException(ObjectNotFoundException exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyModifiedException.class)
    protected ResponseEntity<Map<String, String>> handleGenericException(EntityAlreadyModifiedException exception) {
        return buildErrorResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InterviewBaseException.class)
    protected ResponseEntity<Map<String, String>> handleGenericException(InterviewBaseException exception) {
        log.error("Generic exception occurred!", exception);
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleBeanValidationException(MethodArgumentNotValidException exception) {
        log.error("Bean validation exception occurred!", exception);
        var message = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return buildErrorResponse(ErrorCode.INT_0004, message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Map<String, String>> handleGenericException(Exception exception) {
        log.error("Unexpected exception occurred!", exception);
        return buildErrorResponse(new InterviewBaseException(ErrorCode.INT_0000), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    static ResponseEntity<Map<String, String>> buildErrorResponse(InterviewBaseException exception, HttpStatus httpStatus) {
        return buildErrorResponse(exception.getCode(), exception.getCode().getMessage(), httpStatus);
    }

    static ResponseEntity<Map<String, String>> buildErrorResponse(ErrorCode errorCode, String errorMessage, HttpStatus httpStatus) {
        var body = new HashMap<String, String>();
        body.put("errorCode", errorCode.name());
        body.put("errorMessage", errorMessage);
        return new ResponseEntity<>(body, httpStatus);
    }
}
