package ch.bfh.bti7081.s2019.blue.server.persistence.builder;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;

public class MissionBuilder extends Builder<Mission> {

    public MissionBuilder() {
        super(new Mission());
    }

    public MissionBuilder setMissionSeries(MissionSeries series) {
        this.entity.setMissionSeries(series);
        return this;
    }
}
