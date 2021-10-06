package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.location.Location;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "ChargingStation")
@Table(name = "chargingstation")
public class ChargingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "max_number_of_chargers")
    private Integer maxNumberOfChargers;

    @Column(name = "owner_company_name")
    private String ownerCompanyName;

    @OneToMany(mappedBy = "chargingStation")
    private Collection<Charger> chargers;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
