package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.abstracts.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingStationRepository extends CustomRepository<ChargingStation> {
}
