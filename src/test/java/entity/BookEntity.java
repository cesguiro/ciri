package entity;

import es.cesguiro.common.annotations.Id;
import es.cesguiro.common.annotations.TableName;
import es.cesguiro.dao.v40.entity.CiriEntity;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor //Importante para que funcione el método getEntityClass de los Dao
@TableName("books")
public class BookEntity extends CiriEntity {

    @Id
    private String isbn;
    private String title;
    private String synopsis;
    private BigDecimal price;
    private String cover;

    /*private String isbn;
    private String title;
    private String synopsis;
    private BigDecimal price;
    private String cover;*/

}
