package es.cesguiro.dao.entity;

import es.cesguiro.annotations.Id;
import es.cesguiro.annotations.TableName;
import lombok.Getter;
import lombok.ToString;

import java.lang.annotation.Annotation;

@Getter
@ToString
public abstract class CiriEntity {

    public String getTableName() {
       return getAnnotationValue(TableName.class);
    }

    public String getPrimaryKey() {
        return getAnnotationValue(Id.class);
    }

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

}
