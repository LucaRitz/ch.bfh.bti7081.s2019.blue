package ch.bfh.bti7081.s2019.blue.server.validator;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.utils.DateRange;
import ch.bfh.bti7081.s2019.blue.server.utils.EntityWrapper;
import ch.bfh.bti7081.s2019.blue.server.utils.MissionGenerator;
import ch.bfh.bti7081.s2019.blue.server.utils.DateTimeUtil;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class MissionSeriesValidator implements IsValidator<EntityWrapper<MissionSeries>> {

    private final ServerConstants constants;
    private final MissionRepository repository;
    private final MissionSeriesRepository seriesRepository;
    private final MissionGenerator generator;

    @Autowired
    public MissionSeriesValidator(ServerConstants constants, MissionSeriesRepository seriesRepository,
                                  MissionRepository repository, MissionGenerator generator) {
        this.constants = constants;
        this.seriesRepository = seriesRepository;
        this.repository = repository;
        this.generator = generator;
    }

    @Override
    public void validate(@Nonnull EntityWrapper<MissionSeries> entity) {
        List<String> errors = new ArrayList<>();

        validateNoMissionsAfterEndDate(entity.getModified()).ifPresent(errors::add);
        validateMissionSeriesOverlappingWithMission(entity.getModified()).ifPresent(errors::add);
        validateDateTimeRange(entity.getOriginal(), entity.getModified()).ifPresent(errors::add);

        checkErrorsAndThrow(errors);
    }

    @VisibleForTesting
    Optional<String> validateNoMissionsAfterEndDate(MissionSeries entity) {

        List<Mission> missions = repository.findByMissionSeriesId(entity.getId());

        for (Mission mission : missions) {

            if (mission.getStartDate().after(entity.getEndDate())
                    || mission.getEndDate().after(entity.getEndDate())) {

                return Optional.of(constants.missionOutOfMissionSeriesFound());
            }

        }
        return Optional.empty();
    }

    @VisibleForTesting
    Optional<String> validateMissionSeriesOverlappingWithMission(MissionSeries entity) {

        List<Mission> temporaryMissions = generator.generateMissionsFromSeries(
                entity, new DateRange(entity.getStartDate(), entity.getEndDate()));

        List<MissionSeries> series = new ArrayList<>(seriesRepository.findByPatientNumberAndIntersectingDateRange(
                entity.getPatient().getNumber(), entity.getStartDate(), entity.getEndDate()));

        // Remove same series in case of update
        series.removeIf(seriesItem -> seriesItem.getId().equals(entity.getId()));
        List<Mission> existingTemporaryMissions = generator.generateMissionsFromSeries(series, new DateRange(
                entity.getStartDate(), entity.getEndDate()));

        for (Mission newMission : temporaryMissions) {

            Date startDate1 = newMission.getStartDate();
            Date endDate1 = newMission.getEndDate();

            for (Mission existingMission : existingTemporaryMissions) {

                Date startDate2 = existingMission.getStartDate();
                Date endDate2 = existingMission.getEndDate();

                if ((startDate1.equals(startDate2) && endDate1.equals(endDate2))
                        || (startDate1.before(endDate2) && endDate1.after(startDate2))) {

                    return Optional.of(constants.missionSeriesIsOverlappingWithMission());
                }
            }
        }
        return Optional.empty();
    }

    @VisibleForTesting
    Optional<String> validateDateTimeRange(MissionSeries original, MissionSeries modified) {

        LocalDate startDate = DateTimeUtil.getDate(modified.getStartDate());
        LocalDate endDate = DateTimeUtil.getDate(modified.getEndDate());

        if (!endDate.isEqual(startDate) && endDate.isBefore(startDate)) {
            return Optional.of(constants.invalidMissionSeriesDateInterval());
        }

        LocalTime startTime = DateTimeUtil.getTime(modified.getStartDate());
        LocalTime endTime = DateTimeUtil.getTime(modified.getEndDate());

        if (endTime.isBefore(startTime) || endTime.equals(startTime)) {
            return Optional.of(constants.invalidMissionSeriesTimeInterval());
        }

        if (original == null && modified.getStartDate().before(new Date())) {
            return Optional.of(constants.missionSeriesStartDateInThePast());
        }

        return Optional.empty();
    }
}
