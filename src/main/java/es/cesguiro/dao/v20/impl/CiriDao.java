package es.cesguiro.dao.v20.impl;

import es.cesguiro.dao.v20.Ciri;
import es.cesguiro.dao.v20.mapper.EntityMapper;
import es.cesguiro.dao.v20.entity.CiriEntity;
import es.cesguiro.queryBuilder.DB;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public abstract class CiriDao<T extends CiriEntity, PK> implements Ciri<T, PK> {


    private final T entityHelper;
    private final EntityMapper<T> entityMapper;

    public CiriDao(EntityMapper<T> entityMapper) {
        this.entityMapper = entityMapper;
        try {
            entityHelper =  this.getEntityClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
                throw new RuntimeException("Failed to instantiate entity class. Make sure the class has a public default constructor.", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access the default constructor of the entity class. Make sure the default constructor is public.", e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("No default constructor found in the entity class. Ensure that the class has a public default constructor.", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("An exception occurred while invoking the default constructor of the entity class.", e);
            }
    }

    public abstract Class<T> getEntityClass();

    @Override
    public List<T> findAll() {
        ResultSet resultSet = DB
                .table(entityHelper.getTableName())
                .get();
        return entityMapper.toEntityList(resultSet);
    }

    @Override
    public Optional<T> findById(PK id) {
        ResultSet resultSet = DB
                .table(entityHelper.getTableName())
                .where(entityHelper.getIdDBFieldName(), "=", id)
                .get();
        return Optional.ofNullable(entityMapper.toEntity(resultSet));
    }


    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public int delete(T entity) {
        return 0;
    }
}
