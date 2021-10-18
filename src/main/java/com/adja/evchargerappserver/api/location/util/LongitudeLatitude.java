package com.adja.evchargerappserver.api.location.util;

import java.io.Serializable;

public class LongitudeLatitude implements Serializable {
    private double latitude;

    private double longitude;

    public LongitudeLatitude(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
