package com.adja.evchargerappserver.api.electriccar.mock;

import com.adja.evchargerappserver.api.electriccar.ElectricCar;

import java.time.Duration;
import java.time.LocalDateTime;

public class MockElectricCarRepresentationImpl implements MockElectricCarRepresentation {

    private boolean charging;

    private Integer batteryPercentage;

    private final Long id;

    private Integer maxPerformanceOfCharger;
    private final Integer maxChargingSpeedOfCar;
    private final Integer disChargingSpeed;
    private final Integer batteryCapacity;

    private LocalDateTime lastChange;

    private static final boolean testing = true;

    public MockElectricCarRepresentationImpl(ElectricCar car) {
        this.id = car.getId();
        this.maxChargingSpeedOfCar = car.getCarType().getMaxChargingSpeed();
        this.disChargingSpeed = car.getCarType().getDischargingSpeed();
        this.batteryPercentage = car.getBatteryPercentage();
        this.batteryCapacity = car.getCarType().getBatterySize();

        if(car.getCharger() != null) {
            this.maxPerformanceOfCharger = car.getCharger().getChargerType().getMaxChargingSpeed();
            this.charging = true;
        }
        else {
            this.charging = false;
        }

        this.lastChange = LocalDateTime.now();

    }

    @Override
    public boolean adjustBatteryPercentage() {
        boolean changed = false;

        long timeElapsedMillis = Duration.between(this.lastChange, LocalDateTime.now()).toMillis();

        if(this.charging) {
            if(timeElapsedMillis > this.getChargeIntervalMillis() &&
                this.batteryPercentage < 100) {

                ++this.batteryPercentage;
                changed = true;
            }
        }
        else{
            if(timeElapsedMillis > this.getDischargeIntervalMillis() &&
                    this.batteryPercentage > 0) {

                --this.batteryPercentage;
                changed = true;
            }
        }

        if(changed) {
            this.lastChange = LocalDateTime.now();
            System.out.println(this.id + ": interval: " + timeElapsedMillis + ", percentage: " + this.batteryPercentage);
        }
        return changed;
    }

    @Override
    public void chargingStarted(Integer maxPerformanceOfCharger) {
        this.charging = true;
        this.maxPerformanceOfCharger = maxPerformanceOfCharger;
        this.lastChange = LocalDateTime.now();
    }

    @Override
    public void chargingEnded() {
        this.charging = false;
        this.lastChange = LocalDateTime.now();
    }

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public Integer getBatteryPercentage() {
        return this.batteryPercentage;
    }

    private long getDischargeIntervalMillis() {
        double fullToZeroDischargeTime = (double) this.batteryCapacity / (double) this.disChargingSpeed * 3600 * 1000;
        double interval = fullToZeroDischargeTime / 100.0;

        if(testing) {
            interval /= 10;
        }
        return (long) interval;
    }

    private long getChargeIntervalMillis() {
        int speed = Math.min(this.maxChargingSpeedOfCar, this.maxPerformanceOfCharger);
        double zeroToFullChargeTime = (double) this.batteryCapacity / (double) speed * 3600 * 1000;
        double interval = zeroToFullChargeTime / 100.0;

        if(testing) {
            interval /= 10;
        }
        return (long) interval;
    }
}
