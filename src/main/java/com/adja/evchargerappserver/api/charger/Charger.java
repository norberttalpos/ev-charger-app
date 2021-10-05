package com.adja.evchargerappserver.api.charger;

import com.adja.evchargerappserver.api.chargertype.ChargerType;
import com.adja.evchargerappserver.api.chargingstation.ChargingStation;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;

import javax.persistence.*;

public class Charger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "currently_charging_car_ID", nullable = true)
    private ElectricCar currentlyChargingCar;

    @ManyToOne
    @JoinColumn(name = "charger_type_ID", nullable = false)
    private ChargerType chargerType;

    @OneToMany
    @JoinColumn(name = "station_ID", nullable = true)
    private ChargingStation chargingStation;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
