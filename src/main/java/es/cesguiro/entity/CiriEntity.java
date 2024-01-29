package es.cesguiro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class CiriEntity {

    private final String tableName;

    @Setter
    private CiriField primaryKey;

    public CiriEntity(String tableName) {
        this.tableName = tableName;
    }

}
