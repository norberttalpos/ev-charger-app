package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricCarTypeRepository extends JpaRepository<ElectricCar, Long> {
}
