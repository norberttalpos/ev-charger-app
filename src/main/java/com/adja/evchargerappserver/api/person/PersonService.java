package com.adja.evchargerappserver.api.person;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.electriccar.ElectricCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends AbstractService<Person, PersonRepository> {

    @Autowired
    ElectricCarRepository electricCarRepository;

    @Override
    protected boolean validateEntity(Person person) {
        return true;//electricCarRepository.findById(person.getCar().getId()).isPresent() ;
    }
}
