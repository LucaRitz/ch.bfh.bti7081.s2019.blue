package ch.bfh.bti7081.s2019.blue.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class EmployeeDto extends AbstractPerson implements Serializable {

    private String profession;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    @JsonIgnore
    public String getDisplayName() {
        return super.getDisplayName() + ", " + this.getProfession();
    }
}