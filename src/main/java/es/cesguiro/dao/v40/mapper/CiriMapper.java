package es.cesguiro.dao.v40.mapper;

import es.cesguiro.common.annotations.TableName;
import es.cesguiro.dao.v40.entity.CiriEntity;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;

public class CiriMapper{

    //private final CiriEntityFactory<T> ciriEntityFactory;
    private CiriEntity ciriEntity;

    public CiriMapper(CiriEntity ciriEntity) {
        this.ciriEntity = ciriEntity;
    }

    public CiriEntity toCiriEntity(ResultSet resultSet) {
        /*if(resultSet == null) {
            return null;
        }
        T ciriEntity = ciriEntityFactory.createEntity();

        try {
            Field[] fields = ciriEntity.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                CiriField ciriField = new CiriField(field.getName(), resultSet.getObject(field.getName()));
                field.set(ciriEntity, ciriField);
            }
            return ciriEntity;
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException("Error al mapear ResultSet a CiriEntity: " + e.getMessage(), e);
        }*/
        return null;
    }

    /*public List<T> toCiriEntityList(ResultSet resultSet) {
        if(resultSet ==  null) {
            return Collections.emptyList();
        }
        List<T> ciriEntityList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                ciriEntityList.add(toCiriEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Algo no ha funcionado: " + e.getMessage());
        }
        return ciriEntityList;
    }*/

    private String getTableNameFromEntityAnnotation(){
        Class<?> entityType = (Class<?>) ((ParameterizedType) ciriEntity.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        TableName tableNameAnnotation = entityType.getAnnotation(TableName.class);
        if (tableNameAnnotation == null) {
            throw new RuntimeException("La entidad no tiene la anotación TableName");
        }
        return tableNameAnnotation.value();
    }
}
