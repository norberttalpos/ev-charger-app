package com.adja.evchargerappserver.api.notification;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "Notification")
@Table(name = "notification")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Notification extends AbstractEntity {

    @Column(name = "person_ID", nullable = false)
    private Long personToNotifyId;

    @Column(name = "charger_ID", nullable = false)
    private Long observedChargerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Notification that = (Notification) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
