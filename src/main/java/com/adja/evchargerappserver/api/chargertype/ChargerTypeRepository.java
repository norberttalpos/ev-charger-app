package com.adja.evchargerappserver.api.chargertype;

import com.adja.evchargerappserver.api.abstracts.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerTypeRepository extends CustomRepository<ChargerType> {
}
