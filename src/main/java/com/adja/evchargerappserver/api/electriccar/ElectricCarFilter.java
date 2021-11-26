package com.adja.evchargerappserver.api.electriccar;

import lombok.Data;

@Data
public class ElectricCarFilter {
    private String licensePlate;
    private Long driver;
    private Integer minBatteryPercentage;
    private Integer maxBatteryPercentage;
    private Long charger;
    private Boolean currentlyCharging;
    private String carType;
}
