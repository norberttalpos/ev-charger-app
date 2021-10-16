package com.adja.evchargerappserver.api.electriccar;

public class StartChargingRequest {
    private Long chargerId;

    public Long getChargerId() {
        return chargerId;
    }

    public void setChargerId(Long chargerId) {
        this.chargerId = chargerId;
    }
}
