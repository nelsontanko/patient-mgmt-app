package dev.pm.patient.service.dto;

import dev.pm.patient.service.config.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Nelson Tanko
 */
public class PatientDTO {
    private PatientDTO(){}

    @Data
    public static class Request{
        @NotBlank(message = "Name is required")
        private String name;
        @NotBlank(message = "Email is required")
        @ValidEmail
        private String email;
        @NotBlank(message = "Address is required")
        private String address;
        @NotBlank(message = "Date of birth is required")
        private String dateOfBirth;
        private String registeredDate;
    }

    @Data
    public static class UpdateRequest{
        private String name;
        @ValidEmail
        private String email;
        private String address;
        private String dateOfBirth;
    }

    @Data
    public static class Response{
        private String id;
        private String name;
        private String email;
        private String address;
        private String dateOfBirth;
    }
}
