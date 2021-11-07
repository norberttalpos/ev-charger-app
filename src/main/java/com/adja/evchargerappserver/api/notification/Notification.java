package com.adja.evchargerappserver.api.notification;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Notification")
@Table(name = "notification")
public class Notification extends AbstractEntity {

    @Column(name = "person_ID", nullable = false)
    private Long personToNotifyId;

    @Column(name = "charger_ID", nullable = false)
    private Long observedChargerId;

    public Long getPersonToNotifyId() {
        return personToNotifyId;
    }

    public void setPersonToNotifyId(Long personToNotifyId) {
        this.personToNotifyId = personToNotifyId;
    }

    public Long getObservedChargerId() {
        return observedChargerId;
    }

    public void setObservedChargerId(Long observedChargerId) {
        this.observedChargerId = observedChargerId;
    }
}
