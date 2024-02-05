package ciriDao.v20.mapper;

import ciriDao.v20.entity.PublisherEntity;
import es.cesguiro.dao.v20.entity.CiriEntity;
import es.cesguiro.dao.v20.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherMapper extends EntityMapper {
    @Override
    public CiriEntity toEntity(ResultSet resultSet) {
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
