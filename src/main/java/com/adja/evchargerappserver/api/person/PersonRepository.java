package com.adja.evchargerappserver.api.person;

import com.adja.evchargerappserver.api.abstracts.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends CustomRepository<Person> {
    Optional<Person> findByUsername(String username);
}
