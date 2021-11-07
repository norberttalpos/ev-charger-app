package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.abstracts.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chargingStation")
@Api(value = "/api/chargingStation", tags = "ChargingStation")
public class ChargingStationController extends AbstractController<ChargingStation, ChargingStationFilter, ChargingStationService> {
}
