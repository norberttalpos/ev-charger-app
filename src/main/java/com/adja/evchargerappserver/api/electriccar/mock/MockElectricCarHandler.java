package com.adja.evchargerappserver.api.electriccar.mock;

import com.adja.evchargerappserver.api.electriccar.ElectricCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MockElectricCarHandler extends Thread {

    @Autowired
    private ElectricCarService electricCarService;

    private List<MockElectricCarRepresentation> cars;

    @PostConstruct
    private void init() {
        this.cars = new ArrayList<>();
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.cars.forEach(car -> {
                boolean batteryPercentageChanged = car.adjustBatteryPercentage();
                this.electricCarService.updateAfterCharging(car.getID(), car.getBatteryPercentage());
            });
        }
    }

    public void startCharging(Long id) {
        this.cars.forEach(car -> {
            if(Objects.equals(car.getID(), id))
                car.chargingStarted();
        });
    }

    public void endCharging(Long id) {
        this.cars.forEach(car -> {
            if(Objects.equals(car.getID(), id))
                car.chargingEnded();
        });
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startUp() {
        this.cars.add(new MockElectricCarRepresentationImpl(1L));
        this.cars.add(new MockElectricCarRepresentationImpl(2L));

        this.start();
    }
}
