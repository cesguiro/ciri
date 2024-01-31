package dao;

import entity.BookEntity;
import es.cesguiro.dao.impl.CiriDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
            BookEntity bookEntity = new BookEntity(
                    resultSet.getString("isbn"),
                    resultSet.getString("title"),
                    resultSet.getString("synopsis"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getString("cover")
            );
            return bookEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
