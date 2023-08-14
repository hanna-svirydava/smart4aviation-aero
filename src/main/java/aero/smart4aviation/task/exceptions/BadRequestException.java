package aero.smart4aviation.task.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ClientException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
