package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportDto {

    private Integer id;

    private HealthStatusDto healthStatus;

    private MissionDto mission;

    private EmployeeDto backofficeAgent;

    private ReportStatus status;

    private List<TaskDto> tasks = new ArrayList<>();

    private List<ActionDto> actions = new ArrayList<>();

    private LocalDateTime creationDate;

    private LocalDateTime closedDate;

    private Duration duration;

    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HealthStatusDto getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatusDto healthStatus) {
        this.healthStatus = healthStatus;
    }

    public MissionDto getMission() {
        return mission;
    }

    public void setMission(MissionDto mission) {
        this.mission = mission;
    }

    public EmployeeDto getBackofficeAgent() {
        return backofficeAgent;
    }

    public void setBackofficeAgent(EmployeeDto backofficeAgent) {
        this.backofficeAgent = backofficeAgent;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    public List<ActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ActionDto> actions) {
        this.actions = actions;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
