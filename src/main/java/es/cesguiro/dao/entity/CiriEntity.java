package es.cesguiro.dao.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import es.cesguiro.annotations.Column;
import es.cesguiro.annotations.Id;
import lombok.Getter;
import lombok.ToString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public abstract class CiriEntity {

    public String getAnnotationValue(Class<? extends Annotation> annotationType) {
        Annotation[] annotationList = getClass().getAnnotations();

        for (Annotation annotation : annotationList) {
            if (annotationType.isInstance(annotation)) {
                try {
                    return (String) annotationType.getMethod("value").invoke(annotation);
                } catch (Exception e) {
                    throw new RuntimeException("Error al obtener el valor de la anotación", e);
                }
            }
        }
        throw new RuntimeException("No existe la anotación " + annotationType.getSimpleName() + " en la entidad");
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

    public String getAttributeList(){
        return null;
    }

    public String getAttributeListValues() {
        return null;
    }

}
