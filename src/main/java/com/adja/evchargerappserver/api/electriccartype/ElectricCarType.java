package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.chargertype.ChargerType;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "electricCarType")
@Table(name = "electriccartype")

public class ElectricCarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="battery_size")
    private int batterySize;

    @Column(name="max_charging_speed")
    private int maxChargingSpeed;

    @ManyToMany
    //TODO: nem adja még annyira
    private Collection<ChargerType> compatibleChargerTypes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBatterySize() {
        return batterySize;
    }

    public void setBatterySize(int batterySize) {
        this.batterySize = batterySize;
    }

    public int getMaxChargingSpeed() {
        return maxChargingSpeed;
    }

    public void setMaxChargingSpeed(int maxChargingSpeed) {
        this.maxChargingSpeed = maxChargingSpeed;
    }
}
