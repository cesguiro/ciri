package es.cesguiro.dao.v10.impl;

import es.cesguiro.dao.v10.Ciri;
import es.cesguiro.dao.v10.entity.CiriEntity;
import es.cesguiro.queryBuilder.DB;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CiriDao<T extends CiriEntity, PK> implements Ciri<T, PK> {


    private final T entityHelper;

    protected CiriDao() {
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



    protected abstract Class<T> getEntityClass();

    protected abstract T toEntity(ResultSet resultSet);

    private List<T> toEntityList(ResultSet resultSet) {
        List<T> entityList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T entity = toEntity(resultSet);
                entityList.add(entity);
            }
            return entityList;
        }catch (SQLException e) {
            throw new RuntimeException("Error while converting ResultSet to entity list", e);
        }
    }

    @Override
    public List<T> findAll() {
        ResultSet resultSet = DB
                .table(entityHelper.getTableName())
                .get();
        return toEntityList(resultSet);
    }

    @Override
    public Optional<T> findById(PK id) {
        ResultSet resultSet = DB
                .table(entityHelper.getTableName())
                .find(id);
        return Optional.ofNullable(toEntity(resultSet));
    }

    public Optional<T> findOneByField(String field, Object fieldValue) {
        ResultSet resultSet = DB
                .table(entityHelper.getTableName())
                .where(field, "=", fieldValue)
                .get();
        try {
            if(!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(toEntity(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Error while converting ResultSet to entity", e);
        }
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
