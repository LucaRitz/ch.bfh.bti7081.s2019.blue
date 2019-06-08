package ch.bfh.bti7081.s2019.blue.server.validator;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.server.utils.EntityWrapper;
import ch.bfh.bti7081.s2019.blue.server.utils.MissionGenerator;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    private Optional<String> validateNoMissionsAfterEndDate(MissionSeries entity) {

        List<Mission> missions = repository.findByMissionSeriesId(entity.getId());

        for (Mission mission : missions) {

            if (mission.getStartDate().isAfter(entity.getEndDate())
                    || mission.getEndDate().isAfter(entity.getEndDate())) {

                return Optional.of(constants.missionOutOfMissionSeriesFound());
            }

        }
        return Optional.empty();
    }

    private Optional<String> validateMissionSeriesOverlappingWithMission(MissionSeries entity) {

        List<Mission> temporaryMissions = generator.generateMissionsFromSeries(
                entity, new DateRange(entity.getStartDate(), entity.getEndDate()));

        List<MissionSeries> series = new ArrayList<>(seriesRepository.findByPatientNumberAndIntersectingDateRange(
                entity.getPatient().getNumber(), entity.getStartDate(), entity.getEndDate()));

        // Remove same series in case of update
        series.removeIf(seriesItem -> seriesItem.getId().equals(entity.getId()));
        List<Mission> existingTemporaryMissions = generator.generateMissionsFromSeries(series, new DateRange(
                entity.getStartDate(), entity.getEndDate()));

        for (Mission newMission : temporaryMissions) {

            LocalDateTime startDate1 = newMission.getStartDate();
            LocalDateTime endDate1 = newMission.getEndDate();

            for (Mission existingMission : existingTemporaryMissions) {

                LocalDateTime startDate2 = existingMission.getStartDate();
                LocalDateTime endDate2 = existingMission.getEndDate();

                if ((startDate1.equals(startDate2) && endDate1.equals(endDate2))
                        || (startDate1.isBefore(endDate2) && endDate1.isAfter(startDate2))) {

                    return Optional.of(constants.missionSeriesIsOverlappingWithMission());
                }
            }
        }
        return Optional.empty();
    }

    private Optional<String> validateDateTimeRange(MissionSeries original, MissionSeries modified) {

        LocalDate startDate = modified.getStartDate().toLocalDate();
        LocalDate endDate = modified.getEndDate().toLocalDate();

        if (!endDate.isEqual(startDate) && endDate.isBefore(startDate)) {
            return Optional.of(constants.invalidMissionSeriesDateInterval());
        }

        LocalTime startTime = modified.getStartDate().toLocalTime();
        LocalTime endTime = modified.getEndDate().toLocalTime();

        if (endTime.isBefore(startTime) || endTime.equals(startTime)) {
            return Optional.of(constants.invalidMissionSeriesTimeInterval());
        }

        if (original == null && modified.getStartDate().isBefore(LocalDateTime.now())) {
            return Optional.of(constants.missionSeriesStartDateInThePast());
        }

        return Optional.empty();
    }
}
