package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.location.util.LongitudeLatitude;

public class ChargingStationFilter {
    private Integer maxNumberOfChargers;
    private String ownerCompanyName;
    private LongitudeLatitude point;
    private Double radius;

    public Integer getMaxNumberOfChargers() {
        return maxNumberOfChargers;
    }

    public void setMaxNumberOfChargers(Integer maxNumberOfChargers) {
        this.maxNumberOfChargers = maxNumberOfChargers;
    }

    public String getOwnerCompanyName() {
        return ownerCompanyName;
    }

    public void setOwnerCompanyName(String ownerCompanyName) {
        this.ownerCompanyName = ownerCompanyName;
    }

    public LongitudeLatitude getPoint() {
        return point;
    }

    public void setPoint(LongitudeLatitude point) {
        this.point = point;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}
