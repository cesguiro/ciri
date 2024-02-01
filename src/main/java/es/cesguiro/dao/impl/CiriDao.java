package es.cesguiro.dao.impl;

import es.cesguiro.annotations.Id;
import es.cesguiro.annotations.TableName;
import es.cesguiro.dao.Ciri;
import es.cesguiro.dao.mapper.CiriMapper;
import es.cesguiro.db.DBUtil;
import es.cesguiro.dao.entity.CiriEntity;
import es.cesguiro.queryBuilder.DB;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CiriDao<T extends CiriEntity, PK> implements Ciri<T, PK> {


    @Override
    public List<T> findAll() {
        ResultSet resultSet = DB
                .table(this.getAnnotationValueFromEntity(TableName.class))
                .get();
        return toEntityList(resultSet);
    }

    @Override
    public Optional<T> findById(PK id) {
        //no utilizamos DB.table().find() para poder cambiar de PK
        ResultSet resultSet= DB
                .table(this.getAnnotationValueFromEntity(TableName.class))
                .where(this.getAnnotationValueFromEntity(Id.class), "=", id)
                .get();
        try {
            if(resultSet.next()) {
                return Optional.of(toEntity(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar el registro de la bbdd", e);
        }
    }

    @Override
    public T save(T entity) {
        return null;
        //DB.table(this.getTableNameFromEntity()).insert();
    }

    @Override
    public int delete(T entity) {
        return 0;
    }

    private String getAnnotationValueFromEntity(Class<? extends Annotation> annotationType) {
        try {
            T entity = getEntityClass().getDeclaredConstructor().newInstance();
            return entity.getAnnotationValue(annotationType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Class<T> getEntityClass();

    protected abstract T toEntity(ResultSet resultSet);

    protected T toEntity2(ResultSet resultSet) {
        try {
            T entity = this.getEntityClass().getDeclaredConstructor().newInstance();
            System.out.println(entity.getDatabaseFieldList());
            return null;
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido crear la entidad", e);
        }
    }


    private List<T> toEntityList(ResultSet resultSet) {
        List<T> entityList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T entity = toEntity(resultSet);
                entityList.add(entity);
            }
            return entityList;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
