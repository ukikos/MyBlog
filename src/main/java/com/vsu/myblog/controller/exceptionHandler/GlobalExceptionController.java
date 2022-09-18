package com.vsu.myblog.controller.exceptionHandler;

import com.vsu.myblog.exception.BadRequestException;
import com.vsu.myblog.exception.ErrorMessage;
import com.vsu.myblog.exception.ForbiddenException;
import com.vsu.myblog.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException exception) {
        return exceptionHandler(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException exception) {
        return exceptionHandler(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleForbiddenException(ForbiddenException exception) {
        return exceptionHandler(exception, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<ErrorMessage> exceptionHandler(Exception e, HttpStatus status) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(status.value());
        errorMessage.setMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, status);
    }


}
