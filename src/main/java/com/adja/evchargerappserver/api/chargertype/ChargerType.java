package com.adja.evchargerappserver.api.chargertype;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "ChargerType")
@Table(name = "chargertype")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ChargerType extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="max_charging_speed", nullable = false)
    private int maxChargingSpeed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChargerType that = (ChargerType) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }
}
