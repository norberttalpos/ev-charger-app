package com.adja.evchargerappserver.api.electriccar.mock;

import com.adja.evchargerappserver.EvChargerAppServerApplication;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.chargingstation.ChargingStationService;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.adja.evchargerappserver.api.electriccar.ElectricCarService;
import com.adja.evchargerappserver.api.electriccar.batteryPercentage.BatteryPercentageUpdate;
import com.adja.evchargerappserver.api.electriccar.batteryPercentage.ChargingStateChangeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class MockElectricCarHandler extends Thread {

    @Autowired
    private ElectricCarService electricCarService;

    @Autowired
    private ChargingStateChangeController websocket;

    @Autowired
    private ChargingStationService chargingStationService;

    private List<MockElectricCarRepresentation> cars;

    private final int pollInterval = 400;

    @PostConstruct
    private void init() {
        this.cars = new ArrayList<>();
    }

    @Override
    public void run() {
        if(EvChargerAppServerApplication.charging) {
            while(true) {
                try {
                    Thread.sleep(this.pollInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.cars.forEach(car -> {
                    boolean batteryPercentageChanged = car.adjustBatteryPercentage();
                    if(batteryPercentageChanged) {
                        this.onBatteryPercentageChanged(car);
                    }
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

    private void onBatteryPercentageChanged(ElectricCarRepresentation car) {
        this.electricCarService.persistBatteryPercentageChanges(car.getID(), car.getBatteryPercentage());

        this.websocket.sendChargingStationUpdateFromJava(new BatteryPercentageUpdate(car.getID(), car.getBatteryPercentage()));
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
