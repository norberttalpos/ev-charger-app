package com.adja.evchargerappserver.socket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarBatteryStateChange {
    private Long carId;
    private Long batteryPercentage;
}
