package dev.pm.patient.service.config.exception;

/**
 * @author Nelson Tanko
 */
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
