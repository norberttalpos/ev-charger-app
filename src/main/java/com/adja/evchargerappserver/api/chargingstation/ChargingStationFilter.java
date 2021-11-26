package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.location.util.LongitudeLatitude;
import lombok.Data;

import java.util.List;

@Data
public class ChargingStationFilter {
    private Integer maxNumberOfChargers;
    private String ownerCompanyName;
    private LongitudeLatitude point;
    private Double radius;
    private List<String> chargerTypes;
}
