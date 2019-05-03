package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.util.Date;

public class MissionDto {

    private Integer id;

    private Date startDate;

    private Date endDate;

    private MissionSeriesDto missionSeries;

    private EmployeeDto healthVisitor;

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

    public EmployeeDto getHealthVisitor() {
        return healthVisitor;
    }

    public void setHealthVisitor(EmployeeDto healthVisitor) {
        this.healthVisitor = healthVisitor;
    }

    public MissionSeriesDto getMissionSeries() {
        return missionSeries;
    }

    public void setMissionSeries(MissionSeriesDto missionSeries) {
        this.missionSeries = missionSeries;
    }
}
