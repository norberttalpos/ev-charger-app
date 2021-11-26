package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.chargertype.ChargerType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "ElectricCarType")
@Table(name = "electriccartype")
@Getter
@Setter
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
    private Collection<ChargerType> compatibleChargerTypes;
}
