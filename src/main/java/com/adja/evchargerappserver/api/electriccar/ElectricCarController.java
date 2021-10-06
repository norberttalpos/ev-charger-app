package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/electricCar")
@Api(value = "/api/electricCar", tags = "ElectricCars")
public class ElectricCarController extends AbstractController<ElectricCar,ElectricCarService> {
}
