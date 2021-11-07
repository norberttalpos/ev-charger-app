package com.adja.evchargerappserver.api.electriccar.batteryPercentage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChargingStateChangeController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/topic/chargingStationToServer")
    @SendTo("/topic/chargingStationToClient")
    public ChargingStateChange sendChargingStationUpdate(ChargingStateChange update) {
        return update;
    }

    public void sendChargingStationUpdateFromJava(ChargingStateChange update) {
        this.template.convertAndSend("/topic/chargingStationToServer", update);
    }
}
