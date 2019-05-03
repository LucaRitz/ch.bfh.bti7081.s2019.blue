package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public interface HasBirthdate {

    Date getBirthdate();

    default Integer getAge() {
        LocalDate birthdateLocalDate = getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthdateLocalDate, LocalDate.now()).getYears();
    }
}
