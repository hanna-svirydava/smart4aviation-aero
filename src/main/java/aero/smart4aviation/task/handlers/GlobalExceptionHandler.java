package aero.smart4aviation.task.handlers;

import aero.smart4aviation.task.dto.ApiError;
import aero.smart4aviation.task.exceptions.ClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ApiError> handleClientException(ClientException exception) {
        return new ResponseEntity<>(new ApiError(exception.getMessage()), exception.getStatus());
    }
}
