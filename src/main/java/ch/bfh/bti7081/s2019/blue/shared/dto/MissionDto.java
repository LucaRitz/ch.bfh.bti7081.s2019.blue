package ch.bfh.bti7081.s2019.blue.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MissionDto {

    private Integer id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private MissionSeriesDto missionSeries;

    private EmployeeDto healthVisitor;

    private boolean recommendationsAvailable;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEndDate() {
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

    public boolean isRecommendationsAvailable() {
        return recommendationsAvailable;
    }

    public void setRecommendationsAvailable(boolean recommendationsAvailable) {
        this.recommendationsAvailable = recommendationsAvailable;
    }


    @JsonIgnore
    public String getDisplayName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");

        return this.getMissionSeries().getPatient().getDisplayName()+": " + formatter.format(this.getStartDate()) + " - " + formatter.format(this.getEndDate());
    }
}
