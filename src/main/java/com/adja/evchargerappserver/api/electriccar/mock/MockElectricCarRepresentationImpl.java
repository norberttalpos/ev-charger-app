package com.adja.evchargerappserver.api.electriccar.mock;

public class MockElectricCarRepresentationImpl implements MockElectricCarRepresentation {

    private boolean charging;

    private Integer batteryPercentage;

    private final Long id;

    public MockElectricCarRepresentationImpl(Long id) {
        this.charging = false;
        this.batteryPercentage = 50;
        this.id = id;
    }

    @Override
    public boolean adjustBatteryPercentage() {
        boolean changed = false;
        if(this.batteryPercentage > 0 && this.batteryPercentage < 100) {
            if(this.charging) {
                ++this.batteryPercentage;
                changed = true;
            }
            else {
                --this.batteryPercentage;
                changed = true;
            }
        }

        if(changed) {
            System.out.println(this.id + ": " + this.batteryPercentage);
        }
        return changed;
    }

    @Override
    public void chargingStarted() {
        this.charging = true;
    }

    @Override
    public void chargingEnded() {
        this.charging = false;
    }

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public Integer getBatteryPercentage() {
        return this.batteryPercentage;
    }
}
