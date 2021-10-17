package com.adja.evchargerappserver.api.chargertype;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChargerTypeService extends AbstractService<ChargerType, ChargerTypeFilter, ChargerTypeRepository> {

    @Override
    public Collection<ChargerType> search(ChargerTypeFilter chargerTypeFilter) {
        return null;
    }

    @Override
    protected boolean validateEntity(ChargerType chargerType) {

        return true;
    }
}
