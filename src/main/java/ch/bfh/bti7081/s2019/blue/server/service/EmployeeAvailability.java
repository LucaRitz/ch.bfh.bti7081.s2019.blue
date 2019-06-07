package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;

import java.util.List;

public class EmployeeAvailability {

    private final List<DateRange> occupations;

    public EmployeeAvailability(List<DateRange> occupations) {
        this.occupations = occupations;
    }

    public boolean isAvailable(DateRange dateRange) {
        return occupations.stream()
                .noneMatch(range -> range.intersects(dateRange));
    }

}
