package com.adja.evchargerappserver.api.charger;

import com.adja.evchargerappserver.api.abstracts.CustomRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerRepository extends CustomRepository<Charger> {
}
