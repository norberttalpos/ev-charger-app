package com.adja.evchargerappserver.api.electriccar;

public class ElectricCarFilter {
    private String licensePlate;
    private Long driver;
    private Integer minBatteryPercentage;
    private Integer maxBatteryPercentage;
    private Long charger;
    private String carType;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getDriver() {
        return driver;
    }

    public void setDriver(Long driver) {
        this.driver = driver;
    }

    public Integer getMinBatteryPercentage() {
        return minBatteryPercentage;
    }

    public void setMinBatteryPercentage(Integer minBatteryPercentage) {
        this.minBatteryPercentage = minBatteryPercentage;
    }

    public Long getCharger() {
        return charger;
    }

    public void setCharger(Long charger) {
        this.charger = charger;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getMaxBatteryPercentage() {
        return maxBatteryPercentage;
    }

    public void setMaxBatteryPercentage(Integer maxBatteryPercentage) {
        this.maxBatteryPercentage = maxBatteryPercentage;
    }
}
