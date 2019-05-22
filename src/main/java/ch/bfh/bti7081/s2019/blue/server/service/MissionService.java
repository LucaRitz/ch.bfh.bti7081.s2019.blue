package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MissionService {

    public List<Mission> mergeExistingMissionsWithTemporaryOnes(List<Mission> missions, List<Mission> temporaryMissions) {
        List<Mission> mergedMissions = new ArrayList<>(missions);


        for (Mission temporaryMission : temporaryMissions) {
            boolean isAlreadyAnExistingMission =
                    missions
                            .stream()
                            .anyMatch(existingMission ->
                                    temporaryMission.getStartDate().equals(existingMission.getStartDate()) &&
                                            temporaryMission.getEndDate().equals(existingMission.getEndDate()) &&
                                            temporaryMission.getMissionSeries().getId().equals(existingMission.getMissionSeries().getId())
                            );

            if (!isAlreadyAnExistingMission) {
                mergedMissions.add(temporaryMission);
            }
        }

        return mergedMissions;
    }
}
