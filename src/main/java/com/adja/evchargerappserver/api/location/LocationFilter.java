package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.location.util.LatitudeLongitude;

public class LocationFilter {
    private LatitudeLongitude point;
    private Double radius;

    public LatitudeLongitude getPoint() {
        return point;
    }

    public void setPoint(LatitudeLongitude point) {
        this.point = point;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}
