package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.electriccartype.ElectricCarType;
import com.adja.evchargerappserver.api.person.Person;

import javax.persistence.*;

@Entity(name = "electricCar")
@Table(name = "electriccar")
public class ElectricCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "license_plate")

    private String licensePlate;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Column(name = "battery_percentage")
    private int batteryPercentage;

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    @OneToOne//(mappedBy = "driver_ID")
    private Person driver;

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }

    @ManyToOne
    @JoinColumn(name="car_type_id",nullable = false)
    private ElectricCarType carType;

    public ElectricCarType getCarType() {
        return carType;
    }

    public void setCarType(ElectricCarType carType) {
        this.carType = carType;
    }


}
