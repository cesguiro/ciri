package es.cesguiro.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class CiriField {

    private final String name;

    @Setter
    private Object value;

    public CiriField(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public CiriField(String name) {
        this.name = name;
        this.value = null;
    }


}
