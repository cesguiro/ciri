package ciriDao.v10.dao;

import ciriDao.v10.entity.OrderEntity;
import es.cesguiro.dao.impl.CiriDao;

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