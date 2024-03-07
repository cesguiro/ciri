package ciriDao.v20.entity;

import ciriDao.v20.mapper.BookMapper;
import ciriDao.v20.mapper.PublisherMapper;
import es.cesguiro.common.annotations.Id;
import es.cesguiro.common.annotations.ManyToOne;
import es.cesguiro.common.annotations.TableName;
import es.cesguiro.dao.v20.entity.CiriEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("books")
public class BookEntity extends CiriEntity {

    @Id
    private Integer id;
    private String isbn;
    private String title;
    private String synopsis;
    private BigDecimal price;
    private String cover;

    @ManyToOne(joinColumn = "publisher_id")
    private PublisherEntity publisherEntity;

    //List<AuthorEntity> authorEntityList;

    public BookEntity(int id, String isbn, String title, String synopsis, BigDecimal price, String cover) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.synopsis = synopsis;
        this.price = price;
        this.cover = cover;
    }



    /*@Override
    public Map<String, String> getJavaToDBColumnMapping() {
        return Map.of(
                "id", "id",
                "isbn", "isbn",
                "title", "title",
                "synopsis", "synopsis",
                "price", "price",
                "cover", "cover",
                "publisherEntity", "publisher_id"
        );
    }*/

    public PublisherEntity getPublisherEntity() {
        if(this.publisherEntity == null) {
            this.publisherEntity = this.manyToOne(PublisherEntity.class, new PublisherMapper()).orElse(null);
        }
        return publisherEntity;
    }

   /* public List<AuthorEntity> getAuthorEntityList() {
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
    }*/


}
