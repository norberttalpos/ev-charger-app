package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.chargertype.ChargerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "ElectricCarType")
@Table(name = "electriccartype")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ElectricCarType extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="battery_size", nullable = false)
    private int batterySize;

    @Column(name="max_charging_speed", nullable = false)
    private int maxChargingSpeed;

    @Column(name = "discharging_speed", nullable = false)
    private int dischargingSpeed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cartypejoin",
            joinColumns = @JoinColumn(name = "car_type_id"),
            inverseJoinColumns = @JoinColumn(name = "charger_type_id"))
    @ToString.Exclude
    private Collection<ChargerType> compatibleChargerTypes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ElectricCarType that = (ElectricCarType) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
