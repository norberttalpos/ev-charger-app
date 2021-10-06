package com.adja.evchargerappserver.api.chargertype;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class ChargerTypeService extends AbstractService<ChargerType,ChargerTypeRepository> {

    @Override
    protected boolean validateEntity(ChargerType chargerType) {
        return true;
    }
}
