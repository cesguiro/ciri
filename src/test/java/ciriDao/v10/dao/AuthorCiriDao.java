package ciriDao.v10.dao;

import ciriDao.v10.entity.AuthorEntity;
import ciriDao.v10.entity.PublisherEntity;
import ciriDao.v10.mapper.AuthorMapper;
import es.cesguiro.dao.v10.impl.CiriDao;
import es.cesguiro.dao.v10.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorCiriDao extends CiriDao<AuthorEntity, Integer> {

    public AuthorCiriDao() {
        super(new AuthorMapper());
    }

    @Override
    public Class<AuthorEntity> getEntityClass() {
        return AuthorEntity.class;
    }

    /*@Override
    protected AuthorEntity toEntity(ResultSet resultSet) {
        if(resultSet == null) {
            return null;
        }
        try {
            return new AuthorEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("nationality"),
                    resultSet.getInt("birth_year"),
                    resultSet.getInt("death_year")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error while converting ResultSet to Entity", e);
        }
    }*/
}
