package ch.bfh.bti7081.s2019.blue.server.i18n;

public interface ServerConstants {
    String entityNotFound(int id);

    String missionOutOfMissionSeriesFound();

    String missionSeriesIsOverlappingWithMission();

    String invalidMissionSeriesDateInterval();

    String invalidMissionSeriesTimeInterval();

    String missionSeriesStartDateInThePast();


    String missionSeriesNotAvailable();
    String missionSeriesHasNoPatient();
    String missionAlreadyExists();
    String missionHasNoHealthVisitor();
    String healthVisitorIsOccupied();

    String internalServerError();
}
