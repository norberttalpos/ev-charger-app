package com.adja.evchargerappserver.api.charger;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.chargertype.ChargerType;
import com.adja.evchargerappserver.api.chargingstation.ChargingStation;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "Charger")
@Table(name = "charger")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Charger extends AbstractEntity {
    @OneToOne
    @JoinColumn(name = "currently_charging_car_ID", referencedColumnName = "id")
    private ElectricCar currentlyChargingCar;

    @ManyToOne
    @JoinColumn(name = "charger_type_ID", nullable = false)
    private ChargerType chargerType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "station_ID", nullable = true)
    private ChargingStation chargingStation;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "notification",
            joinColumns = @JoinColumn(name = "charger_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @ToString.Exclude
    private Collection<Charger> personsToNotify;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Charger charger = (Charger) o;
        return getId() != null && Objects.equals(getId(), charger.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
