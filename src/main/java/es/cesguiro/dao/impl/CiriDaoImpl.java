package es.cesguiro.dao.impl;

import es.cesguiro.dao.CiriDao;
import es.cesguiro.db.DBUtil;
import es.cesguiro.entity.CiriEntity;
import es.cesguiro.entity.CiriEntityFactory;
import es.cesguiro.entity.CiriField;
import es.cesguiro.mapper.CiriMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CiriDaoImpl<T extends CiriEntity, PK> implements CiriDao<T, PK> {

    private final DBUtil dbUtil;
    private final CiriMapper<T> ciriMapper;
    private final CiriEntityFactory<T> ciriEntityFactory;

    public CiriDaoImpl(CiriEntityFactory<T> ciriEntityFactory) {
        this.ciriEntityFactory = ciriEntityFactory;
        this.ciriMapper = new CiriMapper<T>(ciriEntityFactory);
        this.dbUtil = new DBUtil();
    }

    @Override
    public List<T> findAll() {
        final String SQL = "SELECT * FROM " + ciriEntityFactory.createEntity().getTableName();
        ResultSet resultSet = dbUtil.select(SQL, null);
        return ciriMapper.toCiriEntityList(resultSet);
    }

    @Override
    public T findById(PK id) {
        T ciriEntity = ciriEntityFactory.createEntity();
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
        }
    }

    @Override
    public T save(T entity) {
        String tableName = ciriEntityFactory.createEntity().getTableName();
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        StringBuilder valuesBuilder = new StringBuilder("VALUES (");
        List<Object> parameterValues = new ArrayList<>();

        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); //para poder acceder a campos privados o protegidos
                String fieldName = field.getName();
                Object fieldValue = field.get(entity);
                if (fieldValue instanceof CiriField) {
                    // Acceder al valor de CiriField
                    Object ciriFieldValue = ((CiriField) fieldValue).getValue();
                    // Agregar el nombre del campo a la sentencia SQL
                    sqlBuilder.append(fieldName).append(", ");
                    // Agregar el valor del campo a la sentencia VALUES
                    valuesBuilder.append("?, ");
                    parameterValues.add(ciriFieldValue);
                }
            }

            // Eliminar la coma adicional al final de la sentencia SQL
            sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());
            valuesBuilder.delete(valuesBuilder.length() - 2, valuesBuilder.length());

            // Completar las sentencias SQL y mostrarlas (puedes realizar la inserción en la base de datos aquí)
            String sql = sqlBuilder.append(") ").append(valuesBuilder.append(");")).toString();
            int generatedId = dbUtil.insert(sql, parameterValues);
            // Obtener la nueva entidad insertada usando el ID generado
            //T newEntity = findById(generatedId);
            System.out.println("Registro insertado con ID: " + generatedId);

            return null;

            //System.out.println("Registro insertado con ID: " + generatedId);
        }catch (IllegalAccessException e) {
                throw new RuntimeException(e);
        }
    }


}
