package com.adja.evchargerappserver.api.electriccar.mock;

public interface MockElectricCarRepresentation extends ElectricCarRepresentation {
    void chargingStarted(Integer maxPerformanceOfChager);
    void chargingEnded();
    boolean adjustBatteryPercentage();
}
