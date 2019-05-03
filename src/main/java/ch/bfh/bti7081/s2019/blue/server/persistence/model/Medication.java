package ch.bfh.bti7081.s2019.blue.server.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 50, unique = true)
    private String name;

    @Column(name = "usage", length = 50)
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
