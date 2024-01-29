package es.cesguiro.dao.impl;

import es.cesguiro.annotations.TableName;
import es.cesguiro.dao.Ciri;
import es.cesguiro.dao.mapper.CiriMapper;
import es.cesguiro.db.DBUtil;
import es.cesguiro.dao.entity.CiriEntity;
import es.cesguiro.queryBuilder.DB;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class CiriDao<T extends CiriEntity, PK> implements Ciri<T, PK> {

    //private final CiriMapper ciriMapper;
    private final DBUtil dbUtil = new DBUtil();

    private final Class<T> entityType;
    //private final CiriMapper<T> ciriMapper;
    //private final CiriEntityFactory<T> ciriEntityFactory;

    /*public CiriDao(CiriEntityFactory<T> ciriEntityFactory) {
        //this.ciriEntityFactory = ciriEntityFactory;
        //this.ciriMapper = new CiriMapper<T>(ciriEntityFactory);
        //this.dbUtil = new DBUtil();

        //this.ciriMapper = new CiriMapper(ciriEntity);
    }*/

    public CiriDao() {
        this.entityType = initEntityType();
    }

    private Class<T> initEntityType() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() {
        ResultSet resultSet = DB.table(this.getTableNameFromAnnotation()).get();
        return toCiriEntityList(resultSet);
    }

    @Override
    public T findById(PK id) {
        /*T ciriEntity = ciriEntityFactory.createEntity();
        final String SQL = "SELECT * FROM " +
                ciriEntity.getTableName() +
                " WHERE " + ciriEntity.getPrimaryKey().getName() +
                "=?";
        ResultSet resultSet = dbUtil.select(SQL, List.of(id));
        try {
            resultSet.next();
            return ciriMapper.toCiriEntity(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        return null;
    }

    @Override
    public T save(T entity) {
       return null;
    }

    @Override
    public int delete(T entity) {
        return 0;
    }

    private String getTableNameFromAnnotation(){
        Class<?> entityType = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        TableName tableNameAnnotation = entityType.getAnnotation(TableName.class);
        if (tableNameAnnotation == null) {
            throw new RuntimeException("La entidad no tiene la anotación TableName");
        }
        return tableNameAnnotation.value();
    }

    public List<T> toCiriEntityList(ResultSet resultSet) {
        List<T> entities = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                T entity = entityType.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);

                    // Verifica si el campo columnName existe en la entidad antes de intentar asignarlo
                    if (!containsField(entity, columnName)) {
                        continue;
                    }

                    Object columnValue = resultSet.getObject(i);
                    setEntityField(entity, columnName, columnValue);
                }
                entities.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear el ResultSet a la entidad", e);
        }
        return entities;
    }

    private boolean containsField(Object entity, String fieldName) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            return field != null;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    private void setEntityField(Object entity, String fieldName, Object value) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (Exception e) {
            throw new RuntimeException("Error al establecer el campo en la entidad", e);
        }
    }

}
