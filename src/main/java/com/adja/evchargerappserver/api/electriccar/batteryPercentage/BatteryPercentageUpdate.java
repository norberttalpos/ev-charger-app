package com.adja.evchargerappserver.api.electriccar.batteryPercentage;

public class BatteryPercentageUpdate {
    private Long carId;
    private int batteryPercentage;

    public BatteryPercentageUpdate(Long carId, int batteryPercentage) {
        this.carId = carId;
        this.batteryPercentage = batteryPercentage;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }
}
