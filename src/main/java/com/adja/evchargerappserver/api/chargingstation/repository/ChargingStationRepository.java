package com.adja.evchargerappserver.api.chargingstation.repository;

import com.adja.evchargerappserver.api.abstracts.AbstractRepository;
import com.adja.evchargerappserver.api.chargingstation.ChargingStation;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingStationRepository extends AbstractRepository<ChargingStation>, ChargingStationRepositoryCustom {
}
