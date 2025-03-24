package dev.pm.patient.service.service;

import dev.pm.patient.service.config.exception.EmailAlreadyExistsException;
import dev.pm.patient.service.config.exception.PatientNotFoundException;
import dev.pm.patient.service.dto.PatientDTO.Request;
import dev.pm.patient.service.dto.PatientDTO.Response;
import dev.pm.patient.service.dto.PatientDTO.UpdateRequest;
import dev.pm.patient.service.entity.Patient;
import dev.pm.patient.service.grpc.BillingServiceGrpcClient;
import dev.pm.patient.service.kafka.KafkaProducer;
import dev.pm.patient.service.mapper.PatientMapper;
import dev.pm.patient.service.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author Nelson Tanko
 */
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
        this.patientMapper = patientMapper;
    }

    @Transactional
    public Response createPatient(Request request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email " + "already exists" + request.getEmail());
        }
        Patient patient = patientMapper.toEntity(request);

        patientRepository.save(patient);
        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(),patient.getEmail());

        kafkaProducer.sendEvent(patient);
        return patientMapper.toDTO(patient);
    }

    public List<Response> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patientMapper::toDTO).toList();
    }

    public Response updatePatients(UUID patientId, UpdateRequest request) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + patientId));

        if (StringUtils.hasText(request.getEmail()) && patientRepository.existsByEmailAndIdNot(request.getEmail(), patientId)) {
            throw new EmailAlreadyExistsException("A patient with this email " + "already exists" + request.getEmail());
        }
        patientMapper.updatePatientFromDTO(request, patient);

        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toDTO(updatedPatient);
    }

    public void deletePatients(UUID patientId) {
        patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        patientRepository.deleteById(patientId);
    }
}
