package dev.pm.patient.service.config.exception;

/**
 * @author Nelson Tanko
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
