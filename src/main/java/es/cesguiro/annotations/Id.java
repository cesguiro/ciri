package es.cesguiro.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //la anotación estará disponible en tiempo de ejecución
@Target(ElementType.TYPE) //Puede ser aplicada a clases
//@Inherited //Para que las clases hijas también las puedan usar
public @interface Id {
    String value();
}