package es.cesguiro.dao.v10;

import es.cesguiro.dao.v10.entity.CiriEntity;

import java.util.List;
import java.util.Optional;

public interface Ciri<T extends CiriEntity, PK> {

    List<T>  findAll();
    Optional<T> findById(PK id);
    T save(T entity);
    int delete(T entity);
}
