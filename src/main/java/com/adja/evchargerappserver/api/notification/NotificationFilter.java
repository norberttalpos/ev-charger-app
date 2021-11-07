package com.adja.evchargerappserver.api.notification;

public class NotificationFilter {

    private Long personId;
    private Long chargerId;

    public NotificationFilter(Long personId, Long chargerId) {
        this.personId = personId;
        this.chargerId = chargerId;
    }

    public NotificationFilter() {
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getChargerId() {
        return chargerId;
    }

    public void setChargerId(Long chargerId) {
        this.chargerId = chargerId;
    }
}
