package es.cesguiro.dao.impl;

import es.cesguiro.annotations.TableName;
import es.cesguiro.dao.Ciri;
import es.cesguiro.dao.mapper.CiriMapper;
import es.cesguiro.db.DBUtil;
import es.cesguiro.dao.entity.CiriEntity;
import es.cesguiro.queryBuilder.DB;

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
        ResultSet resultSet = DB.table(this.getTableNameFromEntity()).get();
        return toEntityList(resultSet);
    }

    @Override
    public Optional<T> findById(PK id) {
        //no utilizamos DB.table().find() para poder cambiar de PK
        ResultSet resultSet= DB
                .table(this.getTableNameFromEntity())
                .where(this.getPrimaryKeyFromEntity(), "=", id)
                .get();
        return Optional.ofNullable(toEntity(resultSet));
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

    private String getTableNameFromEntity() {
        try {
            T entity = getEntityClass().getDeclaredConstructor().newInstance();
            return entity.getTableName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getPrimaryKeyFromEntity() {
        try {
            T entity = getEntityClass().getDeclaredConstructor().newInstance();
            return entity.getPrimaryKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }


}
