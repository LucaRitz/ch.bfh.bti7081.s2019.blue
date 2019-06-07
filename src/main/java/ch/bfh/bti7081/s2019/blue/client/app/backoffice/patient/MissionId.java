package ch.bfh.bti7081.s2019.blue.client.app.backoffice.patient;

public class MissionId {

    private final Type type;
    private final Integer seriesId;
    private final String missionId;

    public MissionId(Type type, Integer seriesId, String missionId) {
        this.type = type;
        this.seriesId = seriesId;
        this.missionId = missionId;
    }

    @Override
    public String toString() {
        return type + ";" + seriesId + ";" + missionId;
    }

    public Type getType() {
        return type;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public Integer getMissionId() {
        return Integer.parseInt(missionId);
    }

    public enum Type {
        MISSION,
        MISSION_SERIES
    }
}
