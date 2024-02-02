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
import java.util.*;

public abstract class CiriDao<T extends CiriEntity, PK> implements Ciri<T, PK> {


    //Instancia de la entidad para acceder a sus anotaciones, campos....
    private final T entityHelper;

    protected CiriDao() {
        try {
            entityHelper =  getEntityClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido crear una instancia de la entidad", e);
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
        //no utilizamos DB.table().find() para poder cambiar de PK
        String idDbFieldName;
        try {
            idDbFieldName = entityHelper.getIdDBFieldName();
            ResultSet resultSet= DB
                    .table(entityHelper.getTableName())
                    .where(idDbFieldName, "=", id)
                    .get();
            if(resultSet.next()) {
                return Optional.of(toEntity2(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar el registro de la bbdd", e);
        }
    }

    @Override
    public int save(T entity) {
        Map<String, String> javaToDBColumnMapping = entityHelper.getJavaToDBColumnMapping();
        Map<String, Object> parameters = new HashMap<>();
        for (String entityFieldName : javaToDBColumnMapping.keySet()) {
            String dbColumnName = javaToDBColumnMapping.get(entityFieldName);
            try {
                // Obtener el valor del campo de la entidad usando reflexión
                Field field = entity.getClass().getDeclaredField(entityFieldName);
                field.setAccessible(true);
                Object value = field.get(entity);

                // Agregar el par (nombre de columna, valor) al mapa de parámetros
                parameters.put(dbColumnName, value);
            } catch (Exception e) {
                throw new RuntimeException("Error al obtener el valor del campo " + entityFieldName, e);
            }
        }
        return DB.table(entityHelper.getTableName()).insert(parameters);
    }

    @Override
    public int delete(T entity) {
        return 0;
    }

    protected abstract Class<T> getEntityClass();

    protected abstract T toEntity(ResultSet resultSet);

    protected T toEntity2(ResultSet resultSet) {
        try {
            T entity = this.getEntityInstance();
            Map<String, String> javaToDBColumnMapping = entity.getJavaToDBColumnMapping();
            for (String key : javaToDBColumnMapping.keySet()) {
                String dbFieldName = javaToDBColumnMapping.get(key);
                Field field = entity.getClass().getDeclaredField(key);
                field.setAccessible(true);
                Object value = resultSet.getObject(dbFieldName);
                field.set(entity, value);
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido mapear desde resultSet a Entity", e);
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

    private T getEntityInstance() {
        try {
            return getEntityClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido crear una instancia de la entidad", e);
        }
    }

}
