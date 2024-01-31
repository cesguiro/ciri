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
        // Obtén todas las anotaciones de la clase
        Annotation[] annotations = getClass().getAnnotations();

        // Busca la anotación @TableName y devuelve su valor si está presente
        for (Annotation annotation : annotations) {
            if (annotation instanceof TableName) {
                return ((TableName) annotation).value();
            }
        }
        // La entidad no tiene la anotación @TableName
        throw new RuntimeException("No existe la anotación @TableName en la entidad");
    }

    public String getPrimaryKey() {
        // Obtén todas las anotaciones de la clase
        Annotation[] annotations = getClass().getAnnotations();

        // Busca la anotación @id y devuelve su valor si está presente
        for (Annotation annotation : annotations) {
            if (annotation instanceof Id) {
                return ((Id) annotation).value();
            }
        }
        // La entidad no tiene la anotación @TableName
        throw new RuntimeException("No existe la anotación @Id en la entidad");
    }

}
