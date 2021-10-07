package com.adja.evchargerappserver.api.abstracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;

@Service
public abstract class AbstractService<ENTITY, REPOSITORY extends JpaRepository<ENTITY, Long>> {

    @Autowired
    protected REPOSITORY repository;

    public Collection<ENTITY> getAll() {
        return this.repository.findAll();
    }

    public ENTITY getById(Long id) throws EntityNotFoundException {
        Optional<ENTITY> entityById = this.repository.findById(id);

        if(entityById.isPresent())
            return entityById.get();
        else
            throw new EntityNotFoundException();
    }

    protected abstract boolean validateEntity(ENTITY entity);

    private boolean validatePutRequest(Long id, ENTITY entity) {
        if(this.repository.findById(id).isEmpty())
            return false;

        return this.validateEntity(entity);
    }

    public ENTITY post(ENTITY entity) throws NotValidUpdateException {
        if(this.validateEntity(entity))
            return this.repository.save(entity);
        else
            throw new NotValidUpdateException("");
    }

    public ENTITY put(Long id, ENTITY entity) throws NotValidUpdateException {
        if(this.validatePutRequest(id, entity))
            return this.repository.save(entity);
        else
            throw new NotValidUpdateException("");
    }

    public void deleteById(Long id) throws EntityNotFoundException {
        if(this.repository.findById(id).isEmpty())
            throw new EntityNotFoundException();
        else
            this.repository.deleteById(id);
    }
}
