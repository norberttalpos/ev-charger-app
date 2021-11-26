package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.location.util.LongitudeLatitude;
import lombok.Data;

@Data
public class LocationFilter {
    private LongitudeLatitude point;
    private Double radius;
}
