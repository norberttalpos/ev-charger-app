package com.adja.evchargerappserver.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChargingStateChangeController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/topic/changes/{room}")
    @SendTo("/topic/changes/{room}")
    public ChargingStationStateChange sendChargingStationUpdate(@DestinationVariable String room, ChargingStationStateChange update) {
        return update;
    }

    @MessageMapping("/topic/carBatteryChange/{room}")
    @SendTo("/topic/carBatteryChange/{room}")
    public CarBatteryStateChange sendCarBatteryPercentageUpdate(@DestinationVariable String room, CarBatteryStateChange update) {
        return update;
    }

    public void sendChargingStationUpdateFromJava(ChargingStationStateChange update) {
        this.template.convertAndSend(String.format("/topic/changes/%s",update.getChargingStationId()), update);
    }

    public void sendCarBatteryPercentageUpdateFromJava(CarBatteryStateChange update) {
        this.template.convertAndSend(String.format("/topic/carBatteryChange/%s",update.getCarId()), update);
    }
}
