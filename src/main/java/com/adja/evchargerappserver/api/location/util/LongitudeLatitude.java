package com.adja.evchargerappserver.api.location.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LongitudeLatitude implements Serializable {
    private double longitude;
    private double latitude;
}
