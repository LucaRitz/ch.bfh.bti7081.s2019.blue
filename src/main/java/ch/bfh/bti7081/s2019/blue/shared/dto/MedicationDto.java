package ch.bfh.bti7081.s2019.blue.shared.dto;

public class MedicationDto {

    private Integer id;

    private String name;

    private String usage;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
        return this.usage;
    }

}
