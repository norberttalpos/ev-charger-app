package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.location.util.LongitudeLatitude;

public class LocationFilter {
    private LongitudeLatitude point;
    private Double radius;

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
