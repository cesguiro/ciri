package es.cesguiro.dao.v10.impl;

import es.cesguiro.dao.v10.Ciri;
import es.cesguiro.dao.v10.entity.CiriEntity;
import es.cesguiro.dao.v10.mapper.EntityMapper;
import es.cesguiro.queryBuilder.DB;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
                .find(id);
        return Optional.ofNullable(entityMapper.toEntity(resultSet));
    }


    @Override
    public T save(T entity) {
        String id = entityHelper.getIdDBFieldName();
        Object value = entity.getIdValue();

        if(value == null) {
            /*Map<String, Object> parameters= Map.of(
                    "isbn", isbn,
                    "title", title,
                    "synopsis", synopsis,
                    "publisher_id", publisher_id,
                    "price", price,
                    "cover", cover
            );*/
        }
        ResultSet resultSet = DB.table(entityHelper.getTableName())
                .find(value);
        if(resultSet == null) {
            //error al actualizar
        } else {
            //actualizar
        }
        return null;
    }

    @Override
    public int delete(T entity) {
        return 0;
    }
}
