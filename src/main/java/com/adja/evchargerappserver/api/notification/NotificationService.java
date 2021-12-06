package com.adja.evchargerappserver.api.notification;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.charger.ChargerService;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.adja.evchargerappserver.api.person.Person;
import com.adja.evchargerappserver.api.person.PersonService;
import com.adja.evchargerappserver.email.EmailSendingService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@Service
public class NotificationService extends AbstractService<Notification, NotificationFilter, NotificationRepository> {

    @Autowired
    private PersonService personService;

    @Autowired
    private ChargerService chargerService;

    @Autowired
    private EmailSendingService emailSendingService;

    @Override
    public Collection<Notification> search(NotificationFilter notificationFilter) {
        QNotification notification = QNotification.notification;
        BooleanBuilder where = new BooleanBuilder();

        if(notificationFilter.getPersonId() != null) {
            where.and(notification.personToNotifyId.eq(notificationFilter.getPersonId()));
        }
        if(notificationFilter.getChargerId() != null) {
            where.and(notification.observedChargerId.eq(notificationFilter.getChargerId()));
        }

        return (Collection<Notification>) this.repository.findAll(where);
    }

    @Override
    protected boolean validateEntity(Notification entity) {
        if(entity.getPersonToNotifyId() == null || entity.getObservedChargerId() == null)
            return false;

        try {
            Person person = this.personService.getById(entity.getPersonToNotifyId());
            Charger charger = this.chargerService.getById(entity.getObservedChargerId());

            NotificationFilter alreadyExistingNotificationFilter = new NotificationFilter(entity.getPersonToNotifyId(), entity.getObservedChargerId());

            if(this.search(alreadyExistingNotificationFilter).size() > 0)
                return false;

            return person.getEmail() != null && charger.getCurrentlyChargingCar() != null;
        }
        catch(EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    protected Notification mapToEntity(Notification persisted, Notification dto) {
        return persisted;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void chargingEnded(Long chargerId) {
        NotificationFilter filter = new NotificationFilter();
        filter.setChargerId(chargerId);
        filter.setPersonId(null);

        Collection<Notification> notificationsOfCharger = this.search(filter);

        notificationsOfCharger.forEach(notification -> {
            Person person = this.personService.getById(notification.getPersonToNotifyId());
            Charger charger = this.chargerService.getById(notification.getObservedChargerId());

            this.emailSendingService.sendChargingEndedNotificationMail(person, charger);

            this.deleteById(notification.getId());
        });
    }

    public void carReached80PercentBattery(ElectricCar car) {
        if(car.getDriver() != null) {
            this.emailSendingService.sendCarReached80PercentBatteryNotificationMail(car);
        }
    }
}
