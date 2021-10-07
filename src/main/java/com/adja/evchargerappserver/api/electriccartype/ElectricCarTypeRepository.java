package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricCarTypeRepository extends JpaRepository<ElectricCar, Long> {
}
