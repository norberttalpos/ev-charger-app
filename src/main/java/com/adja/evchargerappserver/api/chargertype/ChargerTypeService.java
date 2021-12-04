package com.adja.evchargerappserver.api.chargertype;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.location.QLocation;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChargerTypeService extends AbstractService<ChargerType, ChargerTypeFilter, ChargerTypeRepository> {

    @Override
    public Collection<ChargerType> search(ChargerTypeFilter chargerTypeFilter) {
        QChargerType chargerType=QChargerType.chargerType;
        BooleanBuilder where = new BooleanBuilder();

        if(chargerTypeFilter.getMaxChargingSpeed()!=null){
            where.and(chargerType.maxChargingSpeed.eq(chargerTypeFilter.getMaxChargingSpeed()));
        }
        if(chargerTypeFilter.getName()!=null)
            where.and(chargerType.name.contains(chargerTypeFilter.getName()));

        return (Collection<ChargerType>) this.repository.findAll(where);
    }

    @Override
    protected boolean validateEntity(ChargerType chargerType) {

        return true;
    }

    @Override
    protected ChargerType mapToEntity(ChargerType persisted, ChargerType dto) {
        return persisted;
    }
}
