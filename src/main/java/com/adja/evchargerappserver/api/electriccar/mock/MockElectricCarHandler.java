package com.adja.evchargerappserver.api.electriccar.mock;

import com.adja.evchargerappserver.EvChargerAppServerApplication;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.adja.evchargerappserver.api.electriccar.ElectricCarService;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class MockElectricCarHandler extends Thread {

    @Autowired
    private ElectricCarService electricCarService;

    private List<MockElectricCarRepresentation> cars;

    private final int pollInterval = 400;

    @PostConstruct
    private void init() {
        this.cars = new ArrayList<>();
    }

    @Override
    public void run() {
        if(!EvChargerAppServerApplication.testing) {
            while(true) {
                try {
                    Thread.sleep(this.pollInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.cars.forEach(car -> {
                    boolean batteryPercentageChanged = car.adjustBatteryPercentage();
                    if(batteryPercentageChanged)
                        this.electricCarService.updateAfterCharging(car.getID(), car.getBatteryPercentage());
                });
            }
        }
    }

    public void startCharging(ElectricCar persistedCar) {
        this.cars.forEach(car -> {
            if(Objects.equals(car.getID(), persistedCar.getId())){
                car.chargingStarted(persistedCar.getCharger().getChargerType().getMaxChargingSpeed());
            }
        });
    }

    public void endCharging(ElectricCar persistedCar) {
        this.cars.forEach(car -> {
            if(Objects.equals(car.getID(), persistedCar.getId()))
                car.chargingEnded();
        });
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startUp() {

        Collection<ElectricCar> cars = this.electricCarService.getAll();
        cars.forEach(car -> {
            this.cars.add(new MockElectricCarRepresentationImpl(car));
        });

        this.start();
    }
}
