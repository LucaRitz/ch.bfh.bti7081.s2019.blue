package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatientDto extends AbstractPerson implements HasBirthdate {

    private LocalDateTime birthdate;

    private Integer number;

    private List<MedicationDto> medications = new ArrayList<>();

    private List<DiagnoseDto> diagnoses = new ArrayList<>();

    private List<TaskTemplateDto> taskTemplates = new ArrayList<>();

    private DoctorDto doctor;

    private AddressDto address;

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDateTime getBirthdate() {
        return this.birthdate;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return this.number;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public List<MedicationDto> getMedications() {
        return medications;
    }

    public void setMedications(List<MedicationDto> medications) {
        this.medications = medications;
    }

    public List<DiagnoseDto> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<DiagnoseDto> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public DoctorDto getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDto doctor) {
        this.doctor = doctor;
    }

    public List<TaskTemplateDto> getTaskTemplates() {
        return taskTemplates;
    }

    public void setTaskTemplates(List<TaskTemplateDto> taskTemplates) {
        this.taskTemplates = taskTemplates;
    }
}
