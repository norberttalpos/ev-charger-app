package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.CustomRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ElectricCarRepository extends CustomRepository<ElectricCar> {
}
