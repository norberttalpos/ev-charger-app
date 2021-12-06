package com.adja.evchargerappserver.socket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChargingStationStateChange {
    private Long chargingStationId;
}
