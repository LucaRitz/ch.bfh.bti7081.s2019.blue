package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.time.LocalDateTime;

public class DateRange {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public DateRange(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean contains(LocalDateTime date) {
        return date != null
                && (getStartDate().isBefore(date) || getStartDate().equals(date))
                && (getEndDate().isAfter(date) || getEndDate().equals(date));
    }

    public boolean intersects(DateRange other) {
        return (startDate.isBefore(other.getEndDate()) || startDate.equals(other.getEndDate()))
                && (endDate.isAfter(other.getStartDate()) || endDate.equals(other.getStartDate()));
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
