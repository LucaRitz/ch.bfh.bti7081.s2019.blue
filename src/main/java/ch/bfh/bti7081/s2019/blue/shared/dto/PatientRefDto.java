package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.util.Date;

public class PatientRefDto extends AbstractPerson implements HasBirthdate {

    private Date birthdate;

    private Integer number;

    private AddressRefDto address;

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

    public AddressRefDto getAddress() {
        return address;
    }

    public void setAddress(AddressRefDto address) {
        this.address = address;
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + ", " + this.getAge();
    }
}
