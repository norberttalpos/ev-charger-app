package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.abstracts.AbstractController;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.adja.evchargerappserver.api.electriccar.ElectricCarService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/electricCarType")
@Api(value = "/api/electricCarType", tags = "ElectricCarTypes")
public class ElectricCarTypeController extends AbstractController<ElectricCarType, ElectricCarTypeFilter, ElectricCarTypeService> {
}
