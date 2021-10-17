package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.charger.ChargerRepository;
import com.adja.evchargerappserver.api.location.LatitudeLongitude;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class ChargingStationFilter {
    private Long id;
    private Integer maxNumberOfChargers;
    private String ownerCompanyName;
    private Long charger;
    private LatitudeLongitude point;
    private Double distance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getMaxNumberOfChargers() {
        return maxNumberOfChargers;
    }

    public void setMaxNumberOfChargers(Integer maxNumberOfChargers) {
        this.maxNumberOfChargers = maxNumberOfChargers;
    }

    public Long getCharger() {
        return charger;
    }

    /*@Autowired
    private ChargerRepository chargerRepository;
    public Charger getChargerObject(){
        return chargerRepository.getById(getCharger());
    }*/

    public void setCharger(Long charger) {
        this.charger = charger;
    }

    public String getOwnerCompanyName() {
        return ownerCompanyName;
    }

    public void setOwnerCompanyName(String ownerCompanyName) {
        this.ownerCompanyName = ownerCompanyName;
    }

    public LatitudeLongitude getPoint() {
        return point;
    }

    public void setPoint(LatitudeLongitude point) {
        this.point = point;
    }
}
