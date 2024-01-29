package es.cesguiro.mapper;

import es.cesguiro.entity.CiriEntity;
import es.cesguiro.entity.CiriEntityFactory;
import es.cesguiro.entity.CiriField;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CiriMapper<T extends CiriEntity>{

    private final CiriEntityFactory<T> ciriEntityFactory;

    public CiriMapper(CiriEntityFactory<T> ciriEntityFactory) {
        this.ciriEntityFactory = ciriEntityFactory;
    }

    public T toCiriEntity(ResultSet resultSet) {
        if(resultSet == null) {
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
        }
    }

    public List<T> toCiriEntityList(ResultSet resultSet) {
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
    }
}
