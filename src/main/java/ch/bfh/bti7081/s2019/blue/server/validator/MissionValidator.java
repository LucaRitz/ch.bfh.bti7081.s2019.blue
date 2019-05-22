package ch.bfh.bti7081.s2019.blue.server.validator;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;
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

        if (modified.getStartDate().before(series.getStartDate())
            || modified.getEndDate().after(series.getEndDate())) {

            return Optional.of(constants.missionOutOfMissionSeriesFound());
        }

        return Optional.empty();
    }

    @VisibleForTesting
    private Optional<String> validateHealthVisitorIsAvailable(Mission modified) {

        Employee healthVisitor = modified.getHealthVisitor();
        if (healthVisitor == null) {
            return Optional.of(constants.missionHasNoHealthVisitor());
        }

        return Optional.empty();
    }

    @VisibleForTesting
    private Optional<String> validateMissionNotAlreadyExisting(Mission modified) {

        Integer missionSeriesId = modified.getMissionSeries().getId();
        Optional<Mission> existingMission = repository.findByMissionSeriesId(missionSeriesId)
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
