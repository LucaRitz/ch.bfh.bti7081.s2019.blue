package ch.bfh.bti7081.s2019.blue.shared.dto;

public class TaskTemplateDto {

    private Integer id;

    private String description;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
