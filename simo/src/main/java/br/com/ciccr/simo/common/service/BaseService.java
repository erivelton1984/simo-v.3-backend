package br.com.ciccr.simo.common.service;

import br.com.ciccr.simo.common.exception.ResourceNotFoundException;
import br.com.ciccr.simo.common.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity, ID> {

    protected abstract JpaRepository<T, ID> repository();

    public List<T> findAll() {
        return repository().findAll();
    }

    protected T findOrThrow(ID id, String message) {
        return repository()
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(message));
    }

    protected T save(T entity) {
        return repository().save(entity);
    }

    protected void delete(T entity) {
        repository().delete(entity);
    }

    protected boolean exists(ID id) {
        return repository().existsById(id);
    }

    protected long count() {
        return repository().count();
    }

}
