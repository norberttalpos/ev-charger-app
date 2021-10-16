package com.adja.evchargerappserver.api.electriccar.mock;

public interface MockElectricCarRepresentation extends ElectricCarRepresentation {
    void chargingStarted();
    void chargingEnded();
    boolean adjustBatteryPercentage();
}
