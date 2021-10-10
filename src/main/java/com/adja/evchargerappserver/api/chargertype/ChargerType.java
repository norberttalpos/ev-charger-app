package com.adja.evchargerappserver.api.chargertype;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "ChargerType")
@Table(name = "chargertype")
public class ChargerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="max_charging_speed", nullable = false)
    private int maxChargingSpeed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
