package com.adja.evchargerappserver.api.charger;

public class ChargerFilter {
    private Long currentlyChargingCar;
    private String chargerType;
    private Long chargingStation;
    private Boolean reserved;
    private Boolean observed;

    public Long getCurrentlyChargingCar() {
        return currentlyChargingCar;
    }

    public void setCurrentlyChargingCar(Long currentlyChargingCar) {
        this.currentlyChargingCar = currentlyChargingCar;
    }

    public String getChargerType() {
        return chargerType;
    }

    public void setChargerType(String chargerType) {
        this.chargerType = chargerType;
    }

    public Long getChargingStation() {
        return chargingStation;
    }

    public void setChargingStation(Long chargingStation) {
        this.chargingStation = chargingStation;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Boolean getObserved() {
        return observed;
    }

    public void setObserved(Boolean observed) {
        this.observed = observed;
    }
}
