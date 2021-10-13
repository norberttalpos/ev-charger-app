package com.adja.evchargerappserver.api.location;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.postgresql.geometric.PGpoint;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Location")
@Table(name = "location")
@TypeDef(name = "type", typeClass = PGPointType.class)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coordinates", nullable = false)
    @Type(type = "type")
    private PGpoint coordinates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LatitudeLongitude getCoordinates() {
        return new LatitudeLongitude(this.coordinates.x, this.coordinates.y);
    }

    public void setCoordinates(LatitudeLongitude l) {
        this.coordinates = new PGpoint();
        this.coordinates.x = l.getLatitude();
        this.coordinates.y = l.getLongitude();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getId().equals(location.getId()) && getCoordinates().equals(location.getCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCoordinates());
    }
}
