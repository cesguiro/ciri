package ciriDao.v10.entity;

import ciriDao.v10.dao.BookCiriDao;
import ciriDao.v10.dao.PublisherCiriDao;
import es.cesguiro.common.annotations.Id;
import es.cesguiro.dao.v10.entity.CiriEntity;
import es.cesguiro.queryBuilder.DB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

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

    @Override
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
    }

    /*public PublisherEntity getPublisherEntity() {
        if(publisherEntity == null) {
            PublisherCiriDao publisherCiriDao = new PublisherCiriDao();
            this.setPublisherEntity(publisherCiriDao.findOneByField("id", this.getPublisherEntity().getId()));
        }
        return publisherEntity;
    }*/

}
