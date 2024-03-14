package mikhail.task.controllers;

import mikhail.task.dto.ErrorMessage;
import mikhail.task.exceptions.HarvestResultNotFoundException;
import mikhail.task.exceptions.IncorrectInputFieldException;
import mikhail.task.exceptions.ProductNotFoundException;
import mikhail.task.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> userNotFound(UserNotFoundException e) {
        return entityNotFound(e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> productNotFound(ProductNotFoundException e) {
        return entityNotFound(e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> harvestResultNotFound(HarvestResultNotFoundException e) {
        return entityNotFound(e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> incorrectInputField(IncorrectInputFieldException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage(e.getMessage(),
                        System.currentTimeMillis(),
                        HttpStatus.UNPROCESSABLE_ENTITY
                ));
    }

    @ExceptionHandler
    public ResponseEntity<String> badCredentials(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Incorrect password or email! Perhaps account is locked");
    }

    private ResponseEntity<ErrorMessage> entityNotFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage(),
                        System.currentTimeMillis(),
                        HttpStatus.NOT_FOUND
                ));
    }
}
