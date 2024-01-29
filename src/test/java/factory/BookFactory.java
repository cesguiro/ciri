package factory;

import entity.BookEntity;
import es.cesguiro.entity.CiriEntityFactory;


public class BookFactory implements CiriEntityFactory<BookEntity> {

    @Override
    public BookEntity createEntity() {
        return new BookEntity();
    }
}
