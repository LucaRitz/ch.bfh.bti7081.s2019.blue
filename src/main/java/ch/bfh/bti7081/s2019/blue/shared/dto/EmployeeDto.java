package ch.bfh.bti7081.s2019.blue.shared.dto;

public class EmployeeDto extends AbstractPerson {

    private String profession;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + ", " + this.getProfession();
    }
}