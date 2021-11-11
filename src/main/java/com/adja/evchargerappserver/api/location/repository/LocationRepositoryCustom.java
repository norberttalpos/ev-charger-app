package com.adja.evchargerappserver.api.location.repository;

import com.adja.evchargerappserver.api.location.Location;
import com.adja.evchargerappserver.api.location.LocationFilter;

import java.util.Collection;

public interface LocationRepositoryCustom {
    Collection<Location> filterEntities(LocationFilter locationFilter);
}
