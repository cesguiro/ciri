package dao;

import entity.OrderEntity;
import es.cesguiro.dao.v40.impl.CiriDao;

import java.sql.ResultSet;

public class OrderCiriDao extends CiriDao<OrderEntity, Integer> {
    @Override
    protected Class<OrderEntity> getEntityClass() {
        return OrderEntity.class;
    }

    @Override
    protected OrderEntity toEntity(ResultSet resultSet) {
        return null;
    }
}
