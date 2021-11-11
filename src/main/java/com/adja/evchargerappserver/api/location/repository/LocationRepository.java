package com.adja.evchargerappserver.api.location.repository;

import com.adja.evchargerappserver.api.abstracts.AbstractRepository;
import com.adja.evchargerappserver.api.location.Location;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends AbstractRepository<Location>, LocationRepositoryCustom {
}
