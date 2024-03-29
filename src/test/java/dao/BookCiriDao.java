package dao;

import entity.BookEntity;
import es.cesguiro.dao.v40.impl.CiriDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookCiriDao extends CiriDao<BookEntity, String> {

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
            return new BookEntity(
                    resultSet.getString("isbn"),
                    resultSet.getString("title"),
                    resultSet.getString("synopsis"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getString("cover")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
