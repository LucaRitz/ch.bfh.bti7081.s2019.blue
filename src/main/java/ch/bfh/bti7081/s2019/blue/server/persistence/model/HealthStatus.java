package ch.bfh.bti7081.s2019.blue.server.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "healthstatus")
public class HealthStatus {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "physical_score")
    private int physicalScore;

    @Column(name = "mental_score")
    private int mentalScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPhysicalScore() {
        return physicalScore;
    }

    public void setPhysicalScore(int physicalScore) {
        this.physicalScore = physicalScore;
    }

    public int getMentalScore() {
        return mentalScore;
    }

    public void setMentalScore(int mentalScore) {
        this.mentalScore = mentalScore;
    }
}
