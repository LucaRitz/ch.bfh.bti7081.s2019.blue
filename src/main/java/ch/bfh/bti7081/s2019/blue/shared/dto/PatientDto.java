package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientDto extends AbstractPerson implements HasBirthdate {

    private Date birthdate;

    private Integer number;

    private List<MedicationDto> medications = new ArrayList<>();

    private List<DiagnoseDto> diagnoses = new ArrayList<>();

    private DoctorDto doctor;

    private AddressDto address;

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getBirthdate() {
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
}
