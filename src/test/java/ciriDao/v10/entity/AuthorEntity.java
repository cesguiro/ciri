package ciriDao.v10.entity;

import es.cesguiro.dao.v10.entity.CiriEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity extends CiriEntity {

    private int id;
    private String name;
    private String nationality;
    private int birthYear;
    private Integer deathYear;

    @Override
    public String getTableName() {
        return "authors";
    }

    @Override
    public String getIdDBFieldName() {
        return "id";
    }

    /*@Override
    public Map<String, String> getJavaToDBColumnMapping() {
        return Map.of(
                "id", "id",
                "name", "name",
                "nationality", "nationality",
                "birthYear", "birth_year",
                "deathYear", "death_year"
        );
    }*/
}
