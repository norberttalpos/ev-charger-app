package com.adja.evchargerappserver.email;

import com.adja.evchargerappserver.EvChargerAppServerApplication;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.adja.evchargerappserver.api.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class EmailSendingService {

    @Autowired
    private JavaMailSender javaMailSender;


    private void sendMail(SimpleMailMessage mailMessage) {
        if(!EvChargerAppServerApplication.sendMails)
            return;
        javaMailSender.send(mailMessage);
    }

    public void sendDefaultEmail(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("ev.charger.app@gmail.com");

        this.sendMail(mailMessage);
    }

    public void sendChargingEndedNotificationMail(Person person, Charger charger) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(person.getEmail());
        mailMessage.setSubject("Charging ended at observed charger");
        mailMessage.setText(MessageFormat.format("Charging ended at charger: {0}", charger.getId()));

        mailMessage.setFrom("ev.charger.app@gmail.com");

        this.sendMail(mailMessage);
    }

    public void sendCarReached80PercentBatteryNotificationMail(ElectricCar car) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(car.getDriver().getEmail());
        mailMessage.setSubject("Your car reached 80 percent battery");
        mailMessage.setText("");

        mailMessage.setFrom("ev.charger.app@gmail.com");

        this.sendMail(mailMessage);
    }
}
