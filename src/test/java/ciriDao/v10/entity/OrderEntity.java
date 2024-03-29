package ciriDao.v10.entity;

import es.cesguiro.common.annotations.Column;
import es.cesguiro.common.annotations.Id;
import es.cesguiro.common.annotations.TableName;
import es.cesguiro.dao.v10.entity.CiriEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor //Importante para que funcione el método getEntityClass de los Dao
@TableName("orders")
public class OrderEntity extends CiriEntity {

    @Id
    private Integer id;
    @Column("order_date")
    private Date orderDate;
    private int status;

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String getIdDBFieldName() {
        return null;
    }

    @Override
    public Object getIdValue() {
        return this.id;
    }

    @Override
    public Map<String, String> getJavaToDBColumnMapping() {
        return null;
    }

    /*@Override
    public Map<String, String> getJavaToDBColumnMapping() {
        return null;
    }*/
}
