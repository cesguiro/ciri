package es.cesguiro.dao.v40.entity;

import es.cesguiro.common.annotations.Column;
import es.cesguiro.common.annotations.Id;
import es.cesguiro.common.annotations.TableName;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public abstract class CiriEntity {


    public String getTableName() {
        TableName tableNameAnnotation = getClass().getAnnotation(TableName.class);
        if(tableNameAnnotation != null) {
            return tableNameAnnotation.value();
        }
        throw new RuntimeException("No existe la anotación " + TableName.class.getSimpleName() + " en la entidad");
    }

    private String getDBFieldName(Field field) {
        String fieldName = field.getName();
        if (field.isAnnotationPresent(Column.class)) {
            fieldName = field.getAnnotation(Column.class).value();
        }
        return fieldName;
    }

    public String getIdDBFieldName() {
        Field[] fields = getClass().getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Id.class)) {
                return this.getDBFieldName(field);
            }
        }
        throw new RuntimeException("No existe el atributo id");
    }

    public Map<String, String> getJavaToDBColumnMapping() {
        Map<String, String> fieldMap = new HashMap<>();
        Field[] fields = getClass().getDeclaredFields();

        for(Field field : fields) {
            String attributeName = field.getName();
            String dbFieldName = this.getDBFieldName(field);

            fieldMap.put(attributeName, dbFieldName);
        }
        return fieldMap;
    }

}
