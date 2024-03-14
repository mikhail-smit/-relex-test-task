package mikhail.task.controllers;

import mikhail.task.dto.ErrorMessage;
import mikhail.task.exceptions.HarvestResultNotFoundException;
import mikhail.task.exceptions.ProductNotFoundException;
import mikhail.task.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> useNotFound(UserNotFoundException e) {
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

    private ResponseEntity<ErrorMessage> entityNotFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage(),
                        System.currentTimeMillis(),
                        HttpStatus.NOT_FOUND
                ));
    }
}
