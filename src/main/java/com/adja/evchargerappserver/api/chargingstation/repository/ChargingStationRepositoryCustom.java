package com.adja.evchargerappserver.api.chargingstation.repository;

import com.adja.evchargerappserver.api.chargingstation.ChargingStation;
import com.adja.evchargerappserver.api.chargingstation.ChargingStationFilter;

import java.util.Collection;

public interface ChargingStationRepositoryCustom {
    Collection<ChargingStation> filterEntities(ChargingStationFilter chargingStationFilter);
}
