package ciriDao.v10.entity;

import ciriDao.v10.dao.BookCiriDao;
import ciriDao.v10.dao.PublisherCiriDao;
import ciriDao.v10.mapper.AuthorMapper;
import ciriDao.v10.mapper.PublisherMapper;
import es.cesguiro.common.annotations.Id;
import es.cesguiro.dao.v10.entity.CiriEntity;
import es.cesguiro.queryBuilder.DB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Flow;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity extends CiriEntity {

    private int id;
    private String isbn;
    private String title;
    private String synopsis;
    private BigDecimal price;
    private String cover;
    private PublisherEntity publisherEntity;
    List<AuthorEntity> authorEntityList;

    public BookEntity(int id, String isbn, String title, String synopsis, BigDecimal price, String cover) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.synopsis = synopsis;
        this.price = price;
        this.cover = cover;
    }


    @Override
    public String getTableName() {
        return "books";
    }

    @Override
    public String getIdDBFieldName() {
        return "id";
    }

    public PublisherEntity getPublisherEntity() {
        if(this.publisherEntity == null) {
            this.publisherEntity = (PublisherEntity) this
                    .manyToOne("publishers", "id", this.getId(), new PublisherMapper()).orElse(null);
        }
        return publisherEntity;
    }

    public List<AuthorEntity> getAuthorEntityList() {
        if(this.authorEntityList == null) {
            List<CiriEntity> ciriEntityList = this.manyToMany(
                    "authors",
                    "book_authors",
                    "author_id",
                    "id",
                    "book_id",
                    this.getId(),
                    new AuthorMapper()
            );

            // Crear una nueva lista para almacenar las instancias de AuthorEntity
            List<AuthorEntity> authorEntityList = new ArrayList<>();

            // Iterar sobre la lista resultante y hacer el cast
            for (CiriEntity ciriEntity : ciriEntityList) {
                if (ciriEntity instanceof AuthorEntity) {
                    authorEntityList.add((AuthorEntity) ciriEntity);
                }
            }
            this.authorEntityList = authorEntityList;
        }
        return this.authorEntityList;
    }

    /*@Override
    public Map<String, String> getJavaToDBColumnMapping() {
        return Map.of(
                "id", "id",
                "isbn", "isbn",
                "title", "title",
                "synopsis", "synopsis",
                "publisherId", "publisher_id",
                "price", "price",
                "cover", "cover"
        );
    }*/

    /*public PublisherEntity getPublisherEntity() {
        if(publisherEntity == null) {
            PublisherCiriDao publisherCiriDao = new PublisherCiriDao();
            this.setPublisherEntity(publisherCiriDao.findOneByField("id", this.getPublisherEntity().getId()));
        }
        return publisherEntity;
    }*/

}
