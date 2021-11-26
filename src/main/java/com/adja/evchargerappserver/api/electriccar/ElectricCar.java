package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarType;
import com.adja.evchargerappserver.api.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "ElectricCar")
@Table(name = "electriccar")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ElectricCar extends AbstractEntity {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ElectricCar that = (ElectricCar) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }
}
