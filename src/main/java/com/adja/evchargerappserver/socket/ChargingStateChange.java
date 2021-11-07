package com.adja.evchargerappserver.socket;

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
