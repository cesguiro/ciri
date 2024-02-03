package es.cesguiro.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //la anotación estará disponible en tiempo de ejecución
@Target(ElementType.FIELD) //Puede ser aplicada a atributos
//@Inherited //Para que las clases hijas también las puedan usar
public @interface Column {
    String value();
}
