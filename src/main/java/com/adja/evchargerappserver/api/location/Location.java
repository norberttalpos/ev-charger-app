package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.location.util.GeometryUtil;
import com.adja.evchargerappserver.api.location.util.LatitudeLongitude;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Location")
@Table(name = "location")
public class Location extends AbstractEntity {

    @Column(name = "coordinates", nullable = false)
    private Point coordinates;

    public LatitudeLongitude getCoordinates() {
        return new LatitudeLongitude(this.coordinates.getX(), this.coordinates.getY());
    }

    public void setCoordinates(LatitudeLongitude l) {
        this.coordinates = GeometryUtil.parseLocation(l.getLatitude(), l.getLongitude());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getCoordinates().equals(location.getCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordinates());
    }
}
