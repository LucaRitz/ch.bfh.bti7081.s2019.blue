package ch.bfh.bti7081.s2019.blue.server.validator;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.*;
import ch.bfh.bti7081.s2019.blue.server.utils.EntityWrapper;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class MissionValidator implements IsValidator<EntityWrapper<Mission>> {

    private final ServerConstants constants;
    private final MissionRepository repository;

    @Autowired
    public MissionValidator(ServerConstants constants, MissionRepository repository) {
        this.constants = constants;
        this.repository = repository;
    }

    @Override
    public void validate(@Nonnull EntityWrapper<Mission> entity) {
        List<String> errors = new ArrayList<>();

        validateMissionHasSeries(entity.getModified()).ifPresent(errors::add);
        validateHealthVisitorIsAvailable(entity.getModified()).ifPresent(errors::add);
        validateMissionIsInDateRangeOfSeries(entity.getModified()).ifPresent(errors::add);
        validateMissionNotAlreadyExisting(entity.getModified()).ifPresent(errors::add);

        checkErrorsAndThrow(errors);
    }

    @VisibleForTesting
    private Optional<String> validateMissionHasSeries(Mission modified) {

        MissionSeries series = modified.getMissionSeries();
        if (series == null) {
            return Optional.of(constants.missionSeriesNotAvailable());
        }

        Patient patient = series.getPatient();
        if (patient == null) {
            return Optional.of(constants.missionSeriesHasNoPatient());
        }

        return Optional.empty();
    }


    @VisibleForTesting
    private Optional<String> validateMissionIsInDateRangeOfSeries(Mission modified) {

        MissionSeries series = modified.getMissionSeries();
        if (series == null) {
            return Optional.of(constants.missionSeriesNotAvailable());
        }

        switch (series.getRepetitionType()) {
            case DAILY:
                if (!modified.getStartDate().equals(series.getStartDate())
                        || !modified.getEndDate().equals(series.getEndDate())) {
                    return Optional.of(constants.missionOutOfMissionSeriesFound());
                }
                break;

            default:
                if (modified.getStartDate().isBefore(series.getStartDate())
                        || modified.getEndDate().isAfter(series.getEndDate())) {
                    return Optional.of(constants.missionOutOfMissionSeriesFound());
                }
                break;
        }

        return Optional.empty();
    }

    @VisibleForTesting
    private Optional<String> validateHealthVisitorIsAvailable(Mission modified) {

        Employee healthVisitor = modified.getHealthVisitor();
        if (healthVisitor == null) {
            return Optional.of(constants.missionHasNoHealthVisitor());
        }

        List<Mission> missions = repository.findByHealthVisitorAndIntersectingDateRange(healthVisitor.getId(), modified.getStartDate(), modified.getEndDate());
        if (missions == null || missions.size() > 0) {
            return Optional.of(constants.healthVisitorIsOccupied());
        }

        return Optional.empty();
    }

    @VisibleForTesting
    private Optional<String> validateMissionNotAlreadyExisting(Mission modified) {

        Integer missionSeriesId = modified.getMissionSeries().getId();
        List<Mission> existingMissions = repository.findByMissionSeriesId(missionSeriesId);

        Optional<Mission> existingMission =
                existingMissions
                        .stream()
                        .filter(x -> x.getStartDate().equals(modified.getStartDate()) &&
                                x.getEndDate().equals(modified.getEndDate()))
                        .findFirst();

        if (existingMission.isPresent()) {
            return Optional.of(constants.missionAlreadyExists());
        }

        return Optional.empty();
    }

}
