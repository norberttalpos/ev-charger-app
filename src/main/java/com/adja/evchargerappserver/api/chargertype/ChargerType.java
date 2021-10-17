package com.adja.evchargerappserver.api.chargertype;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "ChargerType")
@Table(name = "chargertype")
public class ChargerType extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="max_charging_speed", nullable = false)
    private int maxChargingSpeed;

    public int getMaxChargingSpeed() {
        return maxChargingSpeed;
    }

    public void setMaxChargingSpeed(int maxChargingSpeed) {
        this.maxChargingSpeed = maxChargingSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChargerType)) return false;
        ChargerType that = (ChargerType) o;
        return getMaxChargingSpeed() == that.getMaxChargingSpeed() && getId().equals(that.getId()) && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getMaxChargingSpeed());
    }
}
