package ciriDao.v10.dao;

import ciriDao.v10.entity.BookEntity;
import ciriDao.v10.entity.PublisherEntity;
import es.cesguiro.dao.v10.impl.CiriDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookCiriDao extends CiriDao<BookEntity, Integer> {

    @Override
    protected Class<BookEntity> getEntityClass() {
        return BookEntity.class;
    }

    @Override
    protected BookEntity toEntity(ResultSet resultSet) {
        if(resultSet == null) {
            return null;
        }
        try {
            PublisherCiriDao publisherCiriDao = new PublisherCiriDao();
            PublisherEntity publisherEntity = publisherCiriDao.findOneByField("id", resultSet.getInt("publisher_id")).orElse(null);
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
    }
}
