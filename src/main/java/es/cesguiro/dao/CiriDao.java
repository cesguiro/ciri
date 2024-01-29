package es.cesguiro.dao;

import es.cesguiro.entity.CiriEntity;

import java.util.List;

public interface CiriDao<T extends CiriEntity, PK> {

    List<T>  findAll();
    T findById(PK id);

    T save(T entity);
}
