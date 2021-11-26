package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.location.util.GeometryUtil;
import com.adja.evchargerappserver.api.location.util.LongitudeLatitude;
import org.hibernate.Hibernate;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Location location = (Location) o;
        return getId() != null && Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
