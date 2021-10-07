package com.adja.evchargerappserver.api.chargertype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerTypeRepository extends JpaRepository<ChargerType,Long> {
}
