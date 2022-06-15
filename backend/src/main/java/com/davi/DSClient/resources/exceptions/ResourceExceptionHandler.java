package com.davi.DSClient.resources.exceptions;

import com.davi.DSClient.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();
        err.setTimeStamp(Instant.now());
        err.setMessage(e.getMessage());
        err.setStatus(status.value());
        err.setError("Object not found");
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
