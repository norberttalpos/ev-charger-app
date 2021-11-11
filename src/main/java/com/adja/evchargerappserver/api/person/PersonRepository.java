package com.adja.evchargerappserver.api.person;

import com.adja.evchargerappserver.api.abstracts.AbstractRepository;

import java.util.Optional;

public interface PersonRepository extends AbstractRepository<Person> {
    Optional<Person> findByUsername(String username);
}
