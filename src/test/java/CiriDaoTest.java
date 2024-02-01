import dao.BookCiriDao;
import es.cesguiro.AppPropertiesReader;
import es.cesguiro.rawSql.RawSql;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import entity.BookEntity;
import java.util.List;
import java.util.Optional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@Log4j2
public class CiriDaoTest {


    //BookFactory bookFactory = new BookFactory();

    BookCiriDao bookCiriDao = new BookCiriDao();

    @BeforeAll
    public static void beforeAll(){
        // Configuración de Flyway
        Flyway flyway = Flyway.configure().dataSource(
                AppPropertiesReader.getProperty("flyway.url"),
                AppPropertiesReader.getProperty("flyway.user"),
                AppPropertiesReader.getProperty("flyway.password")
        ).load();

        // Ejecución de migraciones
        flyway.migrate();

        // Verificar que las migraciones se hayan ejecutado correctamente
        for (MigrationInfo migrationInfo : flyway.info().all()) {
            log.info("Migración ejecutada: " + migrationInfo.getDescription());
        }

    }

    @AfterEach
    public void tearDown(){
        RawSql.rollback();
    }

    @Test
    public void testFindAll() {
        List<BookEntity> bookEntityList = bookCiriDao.findAll();
        assertEquals("9788426418197", bookEntityList.get(1).getIsbn());
    }

    @Test
    public  void testFindById() {
        Optional<BookEntity> bookEntity = bookCiriDao.findById("9786074213485");
        assertEquals("La insorportable levedad del ser", bookEntity.get().getTitle());
    }

    @Test
    public  void testFindByNonExistingIdShouldReturnOptionalEmpty() {
        Optional<BookEntity> bookEntity = bookCiriDao.findById("1111111111111");
        assertFalse(bookEntity.isPresent(), "Se esperaba Optional.empty()");
    }


    @Test
    public void testInsert() {
        BookEntity bookEntity = new BookEntity(
                "1111111111111",
                "Prueba libro insertado",
                "prueba sinopsis",
                new BigDecimal(12.50),
                "image.jpeg"

        );
        bookCiriDao.save(bookEntity);
    }

    @Test
    public void testGetDataBaseFieldListFromEntity() {
        BookEntity bookEntity = new BookEntity();

        List<String> expected = List.of(
                "isbn",
                "title",
                "synopsis",
                "price",
                "cover"
        );
        assertEquals(expected, bookEntity.getDatabaseFieldList());
    }
}
