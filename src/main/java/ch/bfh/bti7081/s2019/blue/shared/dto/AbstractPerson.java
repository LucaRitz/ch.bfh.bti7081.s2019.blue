package ch.bfh.bti7081.s2019.blue.shared.dto;

public abstract class AbstractPerson {


    private Integer id;
    private String firstname;
    private String lastname;


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


    public String getDisplayName() {
        return getFirstname() + " " + getLastname();
    }
}
