package com.adja.evchargerappserver.api.chargertype;

import com.adja.evchargerappserver.api.abstracts.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chargerType")
@Api(value = "/api/chargerType", tags = "ChargerType")
public class ChargerTypeController extends AbstractController<ChargerType,ChargerTypeService> {

}
