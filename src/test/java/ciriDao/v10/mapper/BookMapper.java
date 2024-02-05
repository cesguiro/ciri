package ciriDao.v10.mapper;

import ciriDao.v10.dao.AuthorCiriDao;
import ciriDao.v10.dao.PublisherCiriDao;
import ciriDao.v10.entity.AuthorEntity;
import ciriDao.v10.entity.BookEntity;
import ciriDao.v10.entity.PublisherEntity;
import es.cesguiro.dao.v10.entity.CiriEntity;
import es.cesguiro.dao.v10.mapper.EntityMapper;
import es.cesguiro.queryBuilder.DB;

import java.awt.print.Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookMapper extends EntityMapper<BookEntity> {
    @Override
    public BookEntity toEntity(ResultSet resultSet) {
        if(resultSet == null) {
            return null;
        }
        try {
            //PublisherCiriDao publisherCiriDao = new PublisherCiriDao();
            //PublisherEntity publisherEntity = publisherCiriDao.findOneByField("id", resultSet.getInt("publisher_id")).orElse(null);
            //PublisherEntity publisherEntity = boo
            //AuthorCiriDao authorCiriDao = new AuthorCiriDao();
            /*ResultSet resultSetAuthors = DB
                    .table("authors")
                    .select("authors.*")
                    .join("book_authors", "book_authors.author_id", "authors.id")
                    .join("books", "books.id", "book_authors.book_id")
                    .where("books.id", "=", resultSet.getInt("id"))
                    .get();*/
            /*AuthorMapper authorMapper = new AuthorMapper();
            List<AuthorEntity> authorEntityList = authorMapper.toEntityList(resultSetAuthors);
            return new BookEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("isbn"),
                    resultSet.getString("title"),
                    resultSet.getString("synopsis"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getString("cover"),
                    publisherEntity,
                    authorEntityList
            );*/
            BookEntity bookEntity = new BookEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("isbn"),
                    resultSet.getString("title"),
                    resultSet.getString("synopsis"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getString("cover")
            );
            /*PublisherEntity publisherEntity = (PublisherEntity) bookEntity
                    .hasOne("publishers", "id", bookEntity.getId(), new PublisherMapper());
            bookEntity.setPublisherEntity(publisherEntity);*/
            return bookEntity;
        } catch (SQLException e) {
            throw new RuntimeException("Error while converting ResultSet to Entity", e);
        }
    }
}
