package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.location.util.GeometryUtil;
import com.adja.evchargerappserver.api.location.util.LongitudeLatitude;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Location")
@Table(name = "location")
public class Location extends AbstractEntity {

    @Column(name = "coordinates", nullable = false)
    private Point coordinates;

    public LongitudeLatitude getCoordinates() {
        return new LongitudeLatitude(this.coordinates.getX(), this.coordinates.getY());
    }

    public void setCoordinates(LongitudeLatitude l) {
        this.coordinates = GeometryUtil.parseLocation(l.getLongitude(), l.getLatitude());
    }
}
