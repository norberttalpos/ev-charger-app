package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.location.Location;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "ChargingStation")
@Table(name = "chargingstation")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ChargingStation extends AbstractEntity {

    @Column(name = "max_number_of_chargers", nullable = false)
    private Integer maxNumberOfChargers;

    @Column(name = "owner_company_name", nullable = false)
    private String ownerCompanyName;

    @OneToMany(mappedBy = "chargingStation")
    @ToString.Exclude
    private Collection<Charger> chargers;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChargingStation that = (ChargingStation) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
