package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.chargertype.ChargerType;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "electricCarType")
@Table(name = "electriccartype")

public class ElectricCarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="battery_size", nullable = false)
    private int batterySize;

    @Column(name="max_charging_speed", nullable = false)
    private int maxChargingSpeed;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cartypejoin",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "charger_type_id"))
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

    public Collection<ChargerType> getCompatibleChargerTypes() {
        return compatibleChargerTypes;
    }

    public void setCompatibleChargerTypes(Collection<ChargerType> compatibleChargerTypes) {
        this.compatibleChargerTypes = compatibleChargerTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElectricCarType)) return false;
        ElectricCarType that = (ElectricCarType) o;
        return getBatterySize() == that.getBatterySize() && getMaxChargingSpeed() == that.getMaxChargingSpeed() && getId().equals(that.getId()) && getName().equals(that.getName()) && Objects.equals(getCompatibleChargerTypes(), that.getCompatibleChargerTypes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBatterySize(), getMaxChargingSpeed(), getCompatibleChargerTypes());
    }
}
