package dev.pm.patient.service.controller;

import dev.pm.patient.service.dto.PatientDTO;
import dev.pm.patient.service.dto.PatientDTO.Request;
import dev.pm.patient.service.dto.PatientDTO.Response;
import dev.pm.patient.service.service.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Nelson Tanko
 */
@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Response> createPatient(@Valid @RequestBody Request request){
        return new ResponseEntity<>(patientService.createPatient(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Response>> getPatients(){
        return ResponseEntity.ok(patientService.getPatients());
    }

    @PatchMapping("/{patientId}")
    public ResponseEntity<Response> updatePatients(@PathVariable UUID patientId, @RequestBody PatientDTO.UpdateRequest request){
        return ResponseEntity.ok(patientService.updatePatients(patientId, request));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatients(@PathVariable UUID patientId){
        patientService.deletePatients(patientId);
        return ResponseEntity.noContent().build();
    }
}
