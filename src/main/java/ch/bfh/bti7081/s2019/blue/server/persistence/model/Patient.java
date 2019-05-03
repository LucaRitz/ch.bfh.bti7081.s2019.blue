package ch.bfh.bti7081.s2019.blue.server.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname", length = 50)
    private String firstname;

    @Column(name = "lastname", length = 50)
    private String lastname;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "number", unique = true)
    private Integer number;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "patient")
    private List<TaskTemplate> taskTemplates = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Medication> medications = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Diagnose> diagnoses = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private Doctor doctor;

    @OneToOne
    @JoinColumn
    private Address address;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

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

    public List<TaskTemplate> getTaskTemplates() {
        return taskTemplates;
    }

    public void setTaskTemplates(List<TaskTemplate> taskTemplates) {
        this.taskTemplates = taskTemplates;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public List<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
