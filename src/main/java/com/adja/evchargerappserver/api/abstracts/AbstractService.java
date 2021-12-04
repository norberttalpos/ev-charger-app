package com.adja.evchargerappserver.api.abstracts;

import com.adja.evchargerappserver.api.NotValidUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public abstract class AbstractService<ENTITY extends AbstractEntity, FILTER, REPOSITORY extends AbstractRepository<ENTITY>> {

    @Autowired
    protected REPOSITORY repository;

    public Collection<ENTITY> getAll() {
        return this.repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public abstract Collection<ENTITY> search(FILTER filter);

    public ENTITY getById(Long id) throws EntityNotFoundException {
        Optional<ENTITY> entityById = this.repository.findById(id);

        if(entityById.isPresent())
            return entityById.get();
        else
            throw new EntityNotFoundException();
    }

    protected abstract boolean validateEntity(ENTITY entity);

    public ENTITY post(ENTITY entity) throws NotValidUpdateException {
        if(this.validateEntity(entity)) {
            entity.setId(null);

            Long id = this.repository.save(entity).getId();

            return this.getById(id);
        }
        else
            throw new NotValidUpdateException("");
    }

    public ENTITY put(Long id, ENTITY entity) throws NotValidUpdateException {

        Optional<ENTITY> persistedEntity = this.repository.findById(id);

        if(persistedEntity.isEmpty()) {
            throw new NotValidUpdateException("");
        }
        else {
            if(!Objects.equals(entity.getId(), id)) {
                throw new NotValidUpdateException("");
            }
            else {
                ENTITY persisted = persistedEntity.get();

                ENTITY entityToPersist = this.mapToEntity(persisted, entity);

                if(this.validateEntity(entityToPersist)) {
                    return this.repository.save(entityToPersist);
                }
                else {
                    throw new NotValidUpdateException("");
                }
            }
        }
    }

    public void deleteById(Long id) throws EntityNotFoundException {
        if(this.repository.findById(id).isEmpty())
            throw new EntityNotFoundException();
        else
            this.repository.deleteById(id);
    }

    protected abstract ENTITY mapToEntity(ENTITY persisted, ENTITY dto);
}
