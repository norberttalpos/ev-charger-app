package com.adja.evchargerappserver.socket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChargingStateChange {
    private Long chargingStationId;
}
