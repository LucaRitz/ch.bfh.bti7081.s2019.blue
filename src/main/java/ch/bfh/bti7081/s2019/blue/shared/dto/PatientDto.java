package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientDto implements Person {

    private Integer id;

    private String firstname;

    private String lastname;

    private Date birthdate;

    private Integer number;

    private List<MedicationDto> medications = new ArrayList<>();

    private List<DiagnoseDto> diagnoses = new ArrayList<>();

    private DoctorDto doctor;

    private AddressDto address;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String getFirstname() {
        return this.firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getLastname() {
        return this.lastname;
    }

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
