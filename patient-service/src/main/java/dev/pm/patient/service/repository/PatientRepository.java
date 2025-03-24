package dev.pm.patient.service.repository;

import dev.pm.patient.service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Nelson Tanko
 */
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
}