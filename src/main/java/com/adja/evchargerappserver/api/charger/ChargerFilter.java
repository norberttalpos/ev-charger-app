package com.adja.evchargerappserver.api.charger;

import lombok.Data;

@Data
public class ChargerFilter {
    private Long currentlyChargingCar;
    private String chargerType;
    private Long chargingStation;
    private Boolean reserved;
    private Boolean observed;
}
