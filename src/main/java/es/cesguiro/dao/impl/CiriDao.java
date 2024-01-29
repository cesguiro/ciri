package es.cesguiro.dao.impl;

import es.cesguiro.annotations.TableName;
import es.cesguiro.dao.Ciri;
import es.cesguiro.db.DBUtil;
import es.cesguiro.entity.CiriEntity;
import es.cesguiro.entity.CiriEntityFactory;
import es.cesguiro.mapper.CiriMapper;
import es.cesguiro.queryBuilder.DB;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CiriDao<T extends CiriEntity, PK> implements Ciri<T, PK> {

    private final DBUtil dbUtil = new DBUtil();
    //private final CiriMapper<T> ciriMapper;
    //private final CiriEntityFactory<T> ciriEntityFactory;

    public CiriDao(/*CiriEntityFactory<T> ciriEntityFactory*/) {
        //this.ciriEntityFactory = ciriEntityFactory;
        //this.ciriMapper = new CiriMapper<T>(ciriEntityFactory);
        //this.dbUtil = new DBUtil();
    }

    @Override
    public List<T> findAll() {
        //final String SQL = "SELECT * FROM " + ciriEntityFactory.createEntity().getTableName();
        //ResultSet resultSet = dbUtil.select(SQL, null);


        ResultSet resultSet = DB.table(this.getTableNameFromAnnotation()).get();
        return null;
        //return ciriMapper.toCiriEntityList(resultSet);
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

}
