package es.cesguiro.dao;

import es.cesguiro.dao.entity.CiriEntity;

import java.util.List;

public interface Ciri<T extends CiriEntity, PK> {

    List<T>  findAll();
    T findById(PK id);
    T save(T entity);
    int delete(T entity);
}
