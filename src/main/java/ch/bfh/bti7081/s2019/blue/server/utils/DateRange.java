package ch.bfh.bti7081.s2019.blue.server.utils;

import java.util.Date;

public class DateRange {

    private Date startDate;

    private Date endDate;

    public DateRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean contains(Date date) {
        return date != null
                && (getStartDate().before(date) || getStartDate().equals(date))
                && (getEndDate().after(date) || getEndDate().equals(date));
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
