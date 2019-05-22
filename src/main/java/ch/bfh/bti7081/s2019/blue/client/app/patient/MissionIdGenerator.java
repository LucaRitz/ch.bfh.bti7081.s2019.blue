package ch.bfh.bti7081.s2019.blue.client.app.patient;

import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import com.vaadin.guice.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@UIScope
public class MissionIdGenerator {

    public MissionId generate(MissionDto missionDto) {
        if (missionDto.getId() == null) {
            // Non-planned mission
            return new MissionId(MissionId.Type.MISSION_SERIES, missionDto.getMissionSeries().getId(), UUID.randomUUID().toString());
        } else {
            // Planned mission
            return new MissionId(MissionId.Type.MISSION, missionDto.getMissionSeries().getId(), String.valueOf(missionDto.getId()));
        }
    }

    public MissionId parse(String id) {
        String[] parts = id.split(";");
        return new MissionId(MissionId.Type.valueOf(parts[0]), Integer.parseInt(parts[1]), parts[2]);
    }

}
