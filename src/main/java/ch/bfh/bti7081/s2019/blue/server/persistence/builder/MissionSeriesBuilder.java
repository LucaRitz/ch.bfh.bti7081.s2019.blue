package ch.bfh.bti7081.s2019.blue.server.persistence.builder;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;

public class MissionSeriesBuilder extends Builder<MissionSeries> {

    public MissionSeriesBuilder() {
        super(new MissionSeries());
    }

    public MissionSeriesBuilder setPatient(Patient patient) {
        this.entity.setPatient(patient);
        return this;
    }
}
