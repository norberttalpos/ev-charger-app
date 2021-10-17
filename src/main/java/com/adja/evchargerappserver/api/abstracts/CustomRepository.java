package com.adja.evchargerappserver.api.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CustomRepository<ENTITY extends AbstractEntity> extends JpaRepository<ENTITY, Long>, QuerydslPredicateExecutor<ENTITY> {
}
