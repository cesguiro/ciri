package entity;

import es.cesguiro.common.annotations.Column;
import es.cesguiro.common.annotations.Id;
import es.cesguiro.common.annotations.TableName;
import es.cesguiro.dao.v40.entity.CiriEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor //Importante para que funcione el método getEntityClass de los Dao
@TableName("orders")
public class OrderEntity extends CiriEntity {

    @Id
    private int id;
    @Column("order_date")
    private Date orderDate;
    private int status;
}
