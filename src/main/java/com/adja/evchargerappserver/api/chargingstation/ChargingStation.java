package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.location.Location;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "ChargingStation")
@Table(name = "chargingstation")
public class ChargingStation extends AbstractEntity {

    @Column(name = "max_number_of_chargers", nullable = false)
    private Integer maxNumberOfChargers;

    @Column(name = "owner_company_name", nullable = false)
    private String ownerCompanyName;

    @OneToMany(mappedBy = "chargingStation")
    private Collection<Charger> chargers;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Collection<Charger> getChargers() {
        return chargers;
    }

    public void setChargers(Collection<Charger> chargers) {
        this.chargers = chargers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChargingStation)) return false;
        ChargingStation that = (ChargingStation) o;
        return getId().equals(that.getId()) && getMaxNumberOfChargers().equals(that.getMaxNumberOfChargers()) && getOwnerCompanyName().equals(that.getOwnerCompanyName()) && Objects.equals(getChargers(), that.getChargers()) && getLocation().equals(that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMaxNumberOfChargers(), getOwnerCompanyName(), getChargers(), getLocation());
    }
}
