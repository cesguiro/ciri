package ciriDao.v10.dao;

import ciriDao.v10.entity.OrderEntity;
import ciriDao.v10.mapper.OrderMapper;
import es.cesguiro.dao.v10.impl.CiriDao;
import es.cesguiro.dao.v10.mapper.EntityMapper;

import java.sql.ResultSet;

public class OrderCiriDao extends CiriDao<OrderEntity, Integer> {
    public OrderCiriDao() {
        super(new OrderMapper());
    }

    @Override
    public Class<OrderEntity> getEntityClass() {
        return OrderEntity.class;
    }

    /*@Override
    protected OrderEntity toEntity(ResultSet resultSet) {
        return null;
    }*/
}
