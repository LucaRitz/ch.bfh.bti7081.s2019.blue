package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.util.Date;

public class MissionSeriesDto {

    private Integer id;

    private Date startDate;

    private Date endDate;

    private RepetitionType repetitionType;

    private PatientRefDto patient;

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

    public RepetitionType getRepetitionType() {
        return repetitionType;
    }

    public void setRepetitionType(RepetitionType repetitionType) {
        this.repetitionType = repetitionType;
    }

    public PatientRefDto getPatient() {
        return patient;
    }

    public void setPatient(PatientRefDto patient) {
        this.patient = patient;
    }

}
