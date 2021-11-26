package com.adja.evchargerappserver.api.chargertype;

import lombok.Data;

@Data
public class ChargerTypeFilter {
    private String name;
    private Integer maxChargingSpeed;
}
