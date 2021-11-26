package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.location.Location;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "ChargingStation")
@Table(name = "chargingstation")
@Getter
@Setter
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
}
