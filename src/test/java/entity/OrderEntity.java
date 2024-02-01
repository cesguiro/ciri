package entity;

import es.cesguiro.annotations.Column;
import es.cesguiro.annotations.Id;
import es.cesguiro.annotations.TableName;
import es.cesguiro.dao.entity.CiriEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor //Importante para que funcione el método getEntityClass de los Dao
@TableName("orders")
public class OrderEntity extends CiriEntity {

    @Id
    private String id;
    @Column("order_date")
    private String orderDate;
    private String status;
}
