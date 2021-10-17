package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.abstracts.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
@Api(value = "/api/location", tags = "Locations")
public class LocationController extends AbstractController<Location, LocationFilter, LocationService> {
}
