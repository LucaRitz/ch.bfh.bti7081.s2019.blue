package ch.bfh.bti7081.s2019.blue.server.validator;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.builder.MissionBuilder;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.service.DateRange;
import ch.bfh.bti7081.s2019.blue.server.service.EntityWrapper;
import ch.bfh.bti7081.s2019.blue.server.service.MissionGenerator;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
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
    public List<String> validate(@Nonnull EntityWrapper<MissionSeries> entity) {
        List<String> errors = new ArrayList<>();

        validateNoMissionsAfterEndDate(entity.getModified()).ifPresent(errors::add);
        validateMissionSeriesOverlappingWithMission(entity.getModified()).ifPresent(errors::add);

        return errors;
    }

    @VisibleForTesting
    Optional<String> validateNoMissionsAfterEndDate(MissionSeries entity) {
        Example<Mission> statement = new MissionBuilder().setMissionSeries(entity).build();
        List<Mission> missions = repository.findAll(statement);
        for (Mission mission : missions) {
            if ((mission.getStartDate().compareTo(entity.getEndDate()) > 0) ||
                    (mission.getEndDate().compareTo(entity.getEndDate()) > 0)) {

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
            for (Mission existingMission : existingTemporaryMissions) {
                if(newMission.getStartDate().compareTo((existingMission.getEndDate()))<=0 &&
                        newMission.getEndDate().compareTo((existingMission.getStartDate()))>=0)
                {
                    return Optional.of(constants.missionSeriesIsOverlappingWithMission());
                }
            }
        }
        return Optional.empty();
    }
}
