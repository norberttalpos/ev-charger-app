package com.adja.evchargerappserver.api.chargertype;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "ChargerType")
@Table(name = "chargertype")
@Getter
@Setter
public class ChargerType extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="max_charging_speed", nullable = false)
    private int maxChargingSpeed;
}
