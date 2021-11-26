package com.adja.evchargerappserver.api.charger;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.chargertype.ChargerType;
import com.adja.evchargerappserver.api.chargingstation.ChargingStation;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "Charger")
@Table(name = "charger")
@Getter
@Setter
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
    private Collection<Charger> personsToNotify;
}
