package es.cesguiro.common.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) //la anotación estará disponible en tiempo de ejecución
@Target(ElementType.TYPE) //Puede ser aplicada a clases
//@Inherited //Para que las clases hijas también las puedan usar
public @interface TableName {
    String value();
}
