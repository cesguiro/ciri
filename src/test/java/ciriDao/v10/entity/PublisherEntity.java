package ciriDao.v10.entity;

import es.cesguiro.dao.v10.entity.CiriEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherEntity extends CiriEntity {

    private int id;
    private String name;
    @Override
    public String getTableName() {
        return "publishers";
    }

    @Override
    public String getIdDBFieldName() {
        return "id";
    }

    @Override
    public Map<String, String> getJavaToDBColumnMapping() {
        return Map.of(
                "id", "id",
                "name", "name"
        );
    }
}
