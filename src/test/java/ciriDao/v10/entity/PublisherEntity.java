package ciriDao.v10.entity;

import ciriDao.v10.mapper.AuthorMapper;
import ciriDao.v10.mapper.BookMapper;
import es.cesguiro.dao.v10.entity.CiriEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherEntity extends CiriEntity {

    private Integer id;
    private String name;
    private List<BookEntity> bookEntityList;

    public PublisherEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public String getTableName() {
        return "publishers";
    }

    @Override
    public String getIdDBFieldName() {
        return "id";
    }

    @Override
    public Object getIdValue() {
        return this.id;
    }

    @Override
    public Map<String, String> getJavaToDBColumnMapping() {
        return null;
    }

    public List<BookEntity> getBookEntityList() {
        if(this.bookEntityList == null) {
            List<CiriEntity> ciriEntityList = this.oneToMany(
                    "books",
                    "publisher_id",
                    this.id,
                    new BookMapper()
            );

            // Crear una nueva lista para almacenar las instancias de AuthorEntity
            List<BookEntity> bookEntityList = new ArrayList<>();

            // Iterar sobre la lista resultante y hacer el cast
            for (CiriEntity ciriEntity : ciriEntityList) {
                if (ciriEntity instanceof BookEntity) {
                    bookEntityList.add((BookEntity) ciriEntity);
                }
            }
            this.bookEntityList = bookEntityList;
        }
        return this.bookEntityList;
    }
}
