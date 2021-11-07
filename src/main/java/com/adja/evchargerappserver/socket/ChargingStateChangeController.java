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
    public ChargingStateChange sendChargingStationUpdate(@DestinationVariable String room, ChargingStateChange update) {
        return update;
    }

    public void sendChargingStationUpdateFromJava(ChargingStateChange update) {
        this.template.convertAndSend(String.format("/topic/changes/%s",update.getChargingStationId()), update);
    }
}
