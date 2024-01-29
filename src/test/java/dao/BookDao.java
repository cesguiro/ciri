package dao;

import entity.BookEntity;
import es.cesguiro.dao.impl.CiriDaoImpl;
import factory.BookFactory;

public class BookDao extends CiriDaoImpl<BookEntity, String> {

    public BookDao(BookFactory bookFactory) {
        super(bookFactory);
    }

}
