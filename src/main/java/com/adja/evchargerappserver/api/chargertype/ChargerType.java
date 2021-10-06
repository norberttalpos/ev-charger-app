package com.adja.evchargerappserver.api.chargertype;

import javax.persistence.*;

@Entity(name = "ChargerType")
@Table(name = "chargertype")
public class ChargerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="max_charging_speed")
    private int maxChargingSpeed;

    public int getMaxChargingSpeed() {
        return maxChargingSpeed;
    }

    public void setMaxChargingSpeed(int maxChargingSpeed) {
        this.maxChargingSpeed = maxChargingSpeed;
    }
}
