package dev.pm.patient.service.mapper;

import dev.pm.patient.service.entity.Patient;
import org.mapstruct.*;

import static dev.pm.patient.service.dto.PatientDTO.*;

/**
 * @author Nelson Tanko
 */
@Mapper(componentModel = "spring")
public interface PatientMapper {

    Response toDTO(Patient patient);

    Patient toEntity(Request request);

    @Mapping(target = "registeredDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePatientFromDTO(UpdateRequest request, @MappingTarget Patient patient);
}
