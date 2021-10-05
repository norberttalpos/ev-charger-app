package com.adja.evchargerappserver.api.charger;


import com.adja.evchargerappserver.api.abstracts.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/charger")
@Api(value = "/api/charger", tags = "Chargers")
public class ChargerController extends AbstractController<Charger,ChargerService> {

}
