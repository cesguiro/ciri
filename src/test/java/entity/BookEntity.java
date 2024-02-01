package entity;

import es.cesguiro.annotations.Column;
import es.cesguiro.annotations.Id;
import es.cesguiro.annotations.TableName;
import es.cesguiro.dao.entity.CiriEntity;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor //Importante para que funcione el método getEntityClass de los Dao
@TableName("books")
@Id("isbn")
public class BookEntity extends CiriEntity {

    @Column("isbn")
    private String isbn;
    @Column("title")
    private String title;
    @Column("synopsis")
    private String synopsis;
    @Column("price")
    private BigDecimal price;
    @Column("cover")
    private String cover;

    /*private String isbn;
    private String title;
    private String synopsis;
    private BigDecimal price;
    private String cover;*/

}
