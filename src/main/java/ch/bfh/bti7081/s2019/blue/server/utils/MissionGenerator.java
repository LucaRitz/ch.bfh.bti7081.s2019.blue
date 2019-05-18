package ch.bfh.bti7081.s2019.blue.server.utils;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.RepetitionType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        Date date = missionSeries.getStartDate();

        while (date != null && date.before(viewRange.getEndDate())) {

            if (viewRange.contains(date) && missionSeries.getEndDate().after(date)) {
                missions.add(generateMission(missionSeries, date));
            }

            date = getNextDate(missionSeries.getRepetitionType(), date);
        }

        return missions;
    }

    private Mission generateMission(MissionSeries missionSeries, Date startDate) {

        Date endDate = takeOverTime(missionSeries.getEndDate(), startDate);

        Mission mission = new Mission();
        mission.setMissionSeries(missionSeries);
        mission.setStartDate(startDate);
        mission.setEndDate(endDate);
        return mission;
    }

    private Date takeOverTime(Date source, Date target) {

        Calendar sourceCal = Calendar.getInstance();
        sourceCal.setTime(source);

        Calendar targetCal = Calendar.getInstance();
        targetCal.setTime(target);
        targetCal.set(Calendar.HOUR, sourceCal.get(Calendar.HOUR));
        targetCal.set(Calendar.MINUTE, sourceCal.get(Calendar.MINUTE));
        targetCal.set(Calendar.SECOND, sourceCal.get(Calendar.SECOND));
        targetCal.set(Calendar.MILLISECOND, sourceCal.get(Calendar.MILLISECOND));
        return targetCal.getTime();
    }

    private Date getNextDate(RepetitionType repetitionType, Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        switch (repetitionType) {
            case ONCE:
                return null;
            case DAILY:
                cal.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case WEEKLY:
                cal.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case MONTHLY:
                cal.add(Calendar.MONTH, 1);
                break;
            default:
                throw new IllegalArgumentException("Unknown " + RepetitionType.class + ": " + repetitionType);
        }

        return cal.getTime();
    }

}
