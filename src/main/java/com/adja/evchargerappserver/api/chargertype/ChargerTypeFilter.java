package com.adja.evchargerappserver.api.chargertype;

public class ChargerTypeFilter {
    private String name;
    private Integer maxChargingSpeed;

    public Integer getMaxChargingSpeed() {
        return maxChargingSpeed;
    }

    public void setMaxChargingSpeed(Integer maxChargingSpeed) {
        this.maxChargingSpeed = maxChargingSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
