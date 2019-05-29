package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public interface HasBirthdate {

    LocalDateTime getBirthdate();

    default Integer getAge() {
        LocalDate birthdateLocalDate = getBirthdate().toLocalDate();
        return Period.between(birthdateLocalDate, LocalDate.now()).getYears();
    }
}
