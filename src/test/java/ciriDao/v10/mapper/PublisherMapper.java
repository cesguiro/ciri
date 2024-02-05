package ciriDao.v10.mapper;

import ciriDao.v10.entity.PublisherEntity;
import es.cesguiro.dao.v10.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherMapper extends EntityMapper<PublisherEntity> {
    @Override
    public PublisherEntity toEntity(ResultSet resultSet) {
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
    }
}
