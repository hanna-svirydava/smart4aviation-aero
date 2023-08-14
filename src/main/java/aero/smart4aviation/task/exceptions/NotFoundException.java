package aero.smart4aviation.task.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ClientException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
