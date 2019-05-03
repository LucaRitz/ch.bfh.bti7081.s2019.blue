package ch.bfh.bti7081.s2019.blue.server.persistence.model;

import javax.persistence.*;
import java.nio.file.Path;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "street_name", length = 50)
    private String streetName;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "postal_code")
    private Integer postalCode;

    @Column(name = "house_nr", length = 10)
    private String houseNr;

    @OneToOne(mappedBy = "address")
    private Patient patient;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getPostalCode() {
        return this.postalCode;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getHouseNr() {
        return this.houseNr;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
