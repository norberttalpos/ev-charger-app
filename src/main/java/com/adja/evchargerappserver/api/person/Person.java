package com.adja.evchargerappserver.api.person;


import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity(name = "Person")
@Table(name = "person")

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    private String name;

    public String getName() {return name;}

    public void setName(String name) { this.name = name; }
/*
    private ElectricCar car;

    public ElectricCar getCar() {
        return car;
    }

    public void setCar(ElectricCar car) {
        this.car = car;
    }*/
}
