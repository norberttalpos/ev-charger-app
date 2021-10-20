package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.chargertype.ChargerTypeRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ElectricCarTypeService extends AbstractService<ElectricCarType, ElectricCarTypeFilter, ElectricCarTypeRepository> {
    @Autowired
    ChargerTypeRepository chargerTypeRepository;

    @Override
    public Collection<ElectricCarType> search(ElectricCarTypeFilter filter) {
        QElectricCarType electricCarType = QElectricCarType.electricCarType;
        BooleanBuilder where = new BooleanBuilder();

        if(filter.getName() != null) {
            where.and(electricCarType.name.eq(filter.getName()));
        }

        return (Collection<ElectricCarType>) this.repository.findAll(where);
    }

    @Override
    protected boolean validateEntity(ElectricCarType electricCarType) {

        for (var chargerType:electricCarType.getCompatibleChargerTypes()) {
            if( ! chargerTypeRepository.findById(chargerType.getId()).isPresent() )
                return false;
        }
        return true;
    }
}
