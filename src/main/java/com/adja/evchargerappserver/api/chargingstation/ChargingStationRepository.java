package com.adja.evchargerappserver.api.chargingstation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargingStationRepository extends JpaRepository<ChargingStation, Long> {
}
