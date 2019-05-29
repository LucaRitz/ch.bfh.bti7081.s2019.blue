package ch.bfh.bti7081.s2019.blue.server.utils;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.RepetitionType;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MissionGenerator {

    public List<Mission> generateMissionsFromSeries(List<MissionSeries> missionSeries, DateRange viewRange) {

        return missionSeries.stream()
                .flatMap(series -> generateMissionsFromSeries(series, viewRange).stream())
                .collect(Collectors.toList());
    }

    public List<Mission> generateMissionsFromSeries(MissionSeries missionSeries, DateRange viewRange) {
        List<Mission> missions = new ArrayList<>();

        LocalDateTime date = missionSeries.getStartDate();

        while (date != null && date.isBefore(viewRange.getEndDate())) {

            if (viewRange.contains(date) && missionSeries.getEndDate().isAfter(date)) {
                missions.add(generateMission(missionSeries, date));
            }

            date = getNextDate(missionSeries.getRepetitionType(), date);
        }

        return missions;
    }

    private Mission generateMission(MissionSeries missionSeries, LocalDateTime startDate) {

        LocalDateTime endDate = takeOverTime(missionSeries.getEndDate(), startDate);

        Mission mission = new Mission();
        mission.setMissionSeries(missionSeries);
        mission.setStartDate(startDate);
        mission.setEndDate(endDate);
        return mission;
    }

    private LocalDateTime takeOverTime(LocalDateTime source, LocalDateTime target) {
        return LocalDateTime.of(target.toLocalDate(), source.toLocalTime());
    }

    private LocalDateTime getNextDate(RepetitionType repetitionType, LocalDateTime date) {

        switch (repetitionType) {
            case ONCE:
                return null;
            case DAILY:
                return date.plusDays(1);
            case WEEKLY:
                return date.plusWeeks(1);
            case MONTHLY:
                return  date.plusMonths(1);
            default:
                throw new IllegalArgumentException("Unknown " + RepetitionType.class + ": " + repetitionType);
        }

    }

}
