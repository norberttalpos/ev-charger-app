package com.adja.evchargerappserver.api.electriccar.batteryPercentage;

public class ChargingStateChange {
    private Long chargingStationId;

    public ChargingStateChange(Long chargingStationId) {
        this.chargingStationId = chargingStationId;
    }

    public Long getChargingStationId() {
        return chargingStationId;
    }

    public void setChargingStationId(Long chargingStationId) {
        this.chargingStationId = chargingStationId;
    }
}
