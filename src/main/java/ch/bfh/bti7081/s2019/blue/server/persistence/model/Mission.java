package ch.bfh.bti7081.s2019.blue.server.persistence.model;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mission")
public class Mission {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn
    private MissionSeries missionSeries;

    @ManyToOne
    @JoinColumn
    private Employee healthVisitor;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Employee getHealthVisitor() {
        return healthVisitor;
    }

    public void setHealthVisitor(Employee healthVisitor) {
        this.healthVisitor = healthVisitor;
    }

    public MissionSeries getMissionSeries() {
        return missionSeries;
    }

    public void setMissionSeries(MissionSeries missionSeries) {
        this.missionSeries = missionSeries;
    }
}
