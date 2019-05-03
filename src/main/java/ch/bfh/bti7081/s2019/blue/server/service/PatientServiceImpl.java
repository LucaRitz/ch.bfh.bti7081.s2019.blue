package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.PatientRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.*;
import ch.bfh.bti7081.s2019.blue.shared.dto.*;
import ch.bfh.bti7081.s2019.blue.shared.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PatientRefDto> findAll() {
        List<Patient> entities = repository.findAll();
        return mapToRefs(entities);
    }

    private List<PatientRefDto> mapToRefs(List<Patient> entities) {
        return entities.stream()
                .map(this::mapToRef)
                .collect(Collectors.toList());
    }

    private PatientRefDto mapToRef(Patient entity) {
        PatientRefDto dto = new PatientRefDto();
        dto.setId(entity.getId());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setNumber(entity.getNumber());
        dto.setBirthdate(entity.getBirthdate());
        return dto;
    }

    private PatientDto map(Patient entity) {
        PatientDto dto = new PatientDto();
        dto.setId(entity.getId());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setNumber(entity.getNumber());
        dto.setBirthdate(entity.getBirthdate());
        dto.setDoctor(map(entity.getDoctor()));
        dto.setAddress(map(entity.getAddress()));
        dto.setDiagnoses(entity.getDiagnoses().stream()
                .map(this::map)
                .collect(Collectors.toList()));
        dto.setMedications(entity.getMedications().stream()
                .map(this::map)
                .collect(Collectors.toList()));
        return dto;
    }

    private MedicationDto map(Medication entity) {
        MedicationDto dto = new MedicationDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUsage(entity.getUsage());
        return dto;
    }

    private DiagnoseDto map(Diagnose entity) {
        DiagnoseDto dto = new DiagnoseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    private DoctorDto map(Doctor entity) {
        DoctorDto dto = new DoctorDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setTelephone(entity.getTelephone());
        return dto;
    }

    private AddressDto map(Address entity) {
        AddressDto dto = new AddressDto();
        dto.setId(entity.getId());
        dto.setPostalCode(entity.getPostalCode());
        dto.setCity(entity.getCity());
        dto.setStreetName(entity.getStreetName());
        dto.setHouseNr(entity.getHouseNr());
        return dto;
    }
}
