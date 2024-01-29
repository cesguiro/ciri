package entity;

import es.cesguiro.entity.CiriEntity;
import es.cesguiro.entity.CiriField;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class BookEntity extends CiriEntity {

    private CiriField isbn = new CiriField("isbn");
    private CiriField title = new CiriField("title");
    private CiriField synopsis = new CiriField("synopsis");
    private CiriField price = new CiriField("price");
    private CiriField cover = new CiriField("cover");

    public BookEntity() {
        this.setPrimaryKey(isbn);
    }

    public BookEntity(String isbn, String title, String synopsis, BigDecimal price, String cover) {
        this.isbn = new CiriField("isbn", isbn);
        this.title = new CiriField("title", title);
        this.synopsis = new CiriField("synopsis", synopsis);
        this.price = new CiriField("price", price);
        this.cover = new CiriField("cover", cover);
    }
}
