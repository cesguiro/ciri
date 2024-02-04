package es.cesguiro.dao.v10.entity;

import es.cesguiro.common.annotations.Column;
import es.cesguiro.common.annotations.Id;
import es.cesguiro.common.annotations.TableName;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@ToString
public abstract class CiriEntity {


    public abstract String getTableName();

    /*private String getDBFieldName(Field field) {
    }*/

    public abstract String getIdDBFieldName();

    public abstract Map<String, String> getJavaToDBColumnMapping();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CiriEntity that = (CiriEntity) o;
        return Objects.equals(this.getIdDBFieldName(), that.getIdDBFieldName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getIdDBFieldName());
    }
}
