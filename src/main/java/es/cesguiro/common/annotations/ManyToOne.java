package es.cesguiro.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //la anotación estará disponible en tiempo de ejecución
@Target(ElementType.FIELD) //Puede ser aplicada a clases
public @interface ManyToOne {
    String joinColumn();
}
