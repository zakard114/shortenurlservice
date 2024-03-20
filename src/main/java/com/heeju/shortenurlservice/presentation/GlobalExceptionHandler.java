package com.heeju.shortenurlservice.presentation;

import com.heeju.shortenurlservice.domain.LackOfShortenUrlKeyException;
import com.heeju.shortenurlservice.domain.NotFoundShortenUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LackOfShortenUrlKeyException.class)
    public ResponseEntity<String> handleLackOfShortenUrlKeyException(
            LackOfShortenUrlKeyException ex
    ) {
        // Need a way to notify the developer
        return new ResponseEntity<>("There are not enough shortened URL resources.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundShortenUrlException.class)
    public ResponseEntity<String> handleNotFoundShortenUrlException(
            NotFoundShortenUrlException ex
    ) {
            return new ResponseEntity<>("Shortened URL not found.", HttpStatus.NOT_FOUND);
    }

}
