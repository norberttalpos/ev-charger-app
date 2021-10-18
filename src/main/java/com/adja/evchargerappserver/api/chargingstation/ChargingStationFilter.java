package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.location.util.LongitudeLatitude;

import java.util.List;

public class ChargingStationFilter {
    private Integer maxNumberOfChargers;
    private String ownerCompanyName;
    private LongitudeLatitude point;
    private Double radius;
    private List<String> chargerTypes;

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

    public List<String> getChargerTypes() {
        return chargerTypes;
    }

    public void setChargerTypes(List<String> chargerTypes) {
        this.chargerTypes = chargerTypes;
    }
}
