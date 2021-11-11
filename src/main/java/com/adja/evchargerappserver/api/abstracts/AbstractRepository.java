package com.adja.evchargerappserver.api.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractRepository<ENTITY extends AbstractEntity> extends JpaRepository<ENTITY, Long>, QuerydslPredicateExecutor<ENTITY> {
}
