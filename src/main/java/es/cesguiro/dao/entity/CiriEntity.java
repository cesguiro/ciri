package es.cesguiro.dao.entity;

import es.cesguiro.annotations.Column;
import lombok.Getter;
import lombok.ToString;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getDatabaseFieldList() {
        List<String> databaseFields = new ArrayList<>();
        Annotation[] annotationList = getClass().getAnnotations();
        for (Annotation annotation : annotationList) {
            if(annotation.getClass() == Column.class) {
                try {
                    databaseFields.add((String) Column.class.getMethod("value").invoke(annotation));
                } catch (Exception e) {
                    throw new RuntimeException("Error al obtener el valor del campo", e);
                }
            }
        }
        return databaseFields;
    }

    public String getAttributeList(){
        return null;
    }

    public String getAttributeListValues() {
        return null;
    }

}
