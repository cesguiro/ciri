package ciriDao.v10.dao;

import ciriDao.v10.entity.PublisherEntity;
import ciriDao.v10.mapper.PublisherMapper;
import es.cesguiro.dao.v10.impl.CiriDao;
import es.cesguiro.dao.v10.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherCiriDao extends CiriDao<PublisherEntity, Integer> {
    public PublisherCiriDao() {
        super(new PublisherMapper());
    }

    @Override
    public Class<PublisherEntity> getEntityClass() {
        return PublisherEntity.class;
    }

    /*@Override
    protected PublisherEntity toEntity(ResultSet resultSet) {
        if(resultSet == null) {
            return null;
        }
        try {
            return new PublisherEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error while converting ResultSet to Entity", e);
        }
    }*/
}
