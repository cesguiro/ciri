package ciriDao.v10.dao;

import ciriDao.v10.entity.AuthorEntity;
import ciriDao.v10.entity.BookEntity;
import ciriDao.v10.entity.PublisherEntity;
import ciriDao.v10.mapper.BookMapper;
import es.cesguiro.dao.v10.impl.CiriDao;
import es.cesguiro.dao.v10.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookCiriDao extends CiriDao<BookEntity, Integer> {

    public BookCiriDao() {
        super(new BookMapper());
    }

    @Override
    public Class<BookEntity> getEntityClass() {
        return BookEntity.class;
    }

    /*@Override
    protected BookEntity toEntity(ResultSet resultSet) {
        if(resultSet == null) {
            return null;
        }
        try {
            PublisherCiriDao publisherCiriDao = new PublisherCiriDao();
            AuthorCiriDao authorCiriDao = new AuthorCiriDao();
            PublisherEntity publisherEntity = publisherCiriDao.findOneByField("id", resultSet.getInt("publisher_id")).orElse(null);
            List<AuthorEntity> authorEntityList = authorCiriDao.findListByField("id", resultSet.)
            return new BookEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("isbn"),
                    resultSet.getString("title"),
                    resultSet.getString("synopsis"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getString("cover"),
                    publisherEntity
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error while converting ResultSet to Entity", e);
        }
    }*/
}
