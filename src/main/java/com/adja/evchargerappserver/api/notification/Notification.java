package com.adja.evchargerappserver.api.notification;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Notification")
@Table(name = "notification")
@Getter
@Setter
public class Notification extends AbstractEntity {

    @Column(name = "person_ID", nullable = false)
    private Long personToNotifyId;

    @Column(name = "charger_ID", nullable = false)
    private Long observedChargerId;
}
