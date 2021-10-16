package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarType;
import com.adja.evchargerappserver.api.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "electricCar")
@Table(name = "electriccar")
public class ElectricCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @JsonIgnore
    @OneToOne(mappedBy = "car")
    private Person driver;

    @Column(name = "battery_percentage", nullable = false)
    private int batteryPercentage;

    @JsonIgnore
    @OneToOne(mappedBy = "currentlyChargingCar")
    private Charger charger;

    @ManyToOne
    @JoinColumn(name="car_type_id",nullable = false)
    private ElectricCarType carType;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }

    public ElectricCarType getCarType() {
        return carType;
    }

    public void setCarType(ElectricCarType carType) {
        this.carType = carType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElectricCar)) return false;
        ElectricCar that = (ElectricCar) o;
        return getBatteryPercentage() == that.getBatteryPercentage() && getId().equals(that.getId()) && getLicensePlate().equals(that.getLicensePlate()) && getDriver().equals(that.getDriver()) && getCarType().equals(that.getCarType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLicensePlate(), getDriver(), getBatteryPercentage(), getCarType());
    }

    public Charger getCharger() {
        return charger;
    }

    public void setCharger(Charger charger) {
        this.charger = charger;
    }
}
