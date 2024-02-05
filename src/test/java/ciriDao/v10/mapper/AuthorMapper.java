package ciriDao.v10.mapper;

import ciriDao.v10.entity.AuthorEntity;
import es.cesguiro.dao.v10.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper extends EntityMapper<AuthorEntity> {
    @Override
    public AuthorEntity toEntity(ResultSet resultSet) {
        if(resultSet == null) {
            return null;
        }
        try {
            return new AuthorEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("nationality"),
                    resultSet.getInt("birth_year"),
                    (resultSet.getObject("death_year") == null) ? null: resultSet.getInt("death_year")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error while converting ResultSet to Entity", e);
        }
    }
}
