package com.adja.evchargerappserver.api.charger;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.chargertype.ChargerType;
import com.adja.evchargerappserver.api.chargingstation.ChargingStation;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "Charger")
@Table(name = "charger")
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

    public ChargerType getChargerType() {
        return chargerType;
    }

    public void setChargerType(ChargerType chargerType) {
        this.chargerType = chargerType;
    }

    public ChargingStation getChargingStation() {
        return chargingStation;
    }

    public void setChargingStation(ChargingStation chargingStation) {
        this.chargingStation = chargingStation;
    }

    public ElectricCar getCurrentlyChargingCar() {
        return currentlyChargingCar;
    }

    public void setCurrentlyChargingCar(ElectricCar currentlyChargingCar) {
        this.currentlyChargingCar = currentlyChargingCar;
    }

    public Collection<Charger> getPersonsToNotify() {
        return personsToNotify;
    }

    public void setPersonsToNotify(Collection<Charger> personsToNotify) {
        this.personsToNotify = personsToNotify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Charger)) return false;
        Charger charger = (Charger) o;
        return getId() == charger.getId() && Objects.equals(getCurrentlyChargingCar(), charger.getCurrentlyChargingCar()) && getChargerType().equals(charger.getChargerType()) && Objects.equals(getChargingStation(), charger.getChargingStation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCurrentlyChargingCar(), getChargerType(), getChargingStation());
    }
}
