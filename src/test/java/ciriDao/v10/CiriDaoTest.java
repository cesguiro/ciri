package ciriDao.v10;

import ciriDao.v10.dao.BookCiriDao;
import ciriDao.v10.dao.OrderCiriDao;
import ciriDao.v10.entity.BookEntity;
import ciriDao.v10.entity.OrderEntity;
import ciriDao.v10.entity.PublisherEntity;
import es.cesguiro.common.AppPropertiesReader;
import es.cesguiro.rawSql.RawSql;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Log4j2
public class CiriDaoTest {


    //BookFactory bookFactory = new BookFactory();

    BookCiriDao bookCiriDao = new BookCiriDao();
    OrderCiriDao orderCiriDao = new OrderCiriDao();

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
        PublisherEntity publisherEntity = new PublisherEntity(
                1,
                "Editorial Anagrama"
        );
        int expectedSize = 12;
        assertAll( () -> {
            assertEquals(expectedSize, bookEntityList.size());
            assertEquals("9788433920423", bookEntityList.get(0).getIsbn());
            assertEquals("9788426418197", bookEntityList.get(1).getIsbn());
            assertEquals("9786074213485", bookEntityList.get(2).getIsbn());
            assertEquals(publisherEntity, bookEntityList.get(0).getPublisherEntity());
        });
    }

    @Test
    public  void testFindBookById() {
        Optional<BookEntity> bookEntity = bookCiriDao.findById(1);
        PublisherEntity publisherEntity = new PublisherEntity(
                1,
                "Editorial Anagrama"
        );
        assertAll( () -> {
            assertEquals("9788433920423", bookEntity.get().getIsbn());
            assertEquals(publisherEntity, bookEntity.get().getPublisherEntity());
        });
    }


    @Test
    public  void testFindByNonExistingIdShouldReturnOptionalEmpty() {
        Optional<BookEntity> bookEntity = bookCiriDao.findById(15);
        assertFalse(bookEntity.isPresent(), "Se esperaba Optional.empty()");
    }


    /*@Test
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
    public void testGetJavaToDBColumnMappingFromBookEntity() {
        BookEntity bookEntity = new BookEntity();

        Map<String, String> expected = Map.of(
                "isbn", "isbn",
                "title", "title",
                "synopsis", "synopsis",
                "price", "price",
                "cover", "cover"
        );
        assertEquals(expected, bookEntity.getJavaToDBColumnMapping());
    }


    @Test
    public void testGetJavaToDBColumnMappingFromOrderEntity() {
        OrderEntity orderEntity = new OrderEntity();

        Map<String, String> expected = Map.of(
                "id", "id",
                "orderDate", "order_date",
                "status", "status"
        );
        assertEquals(expected, orderEntity.getJavaToDBColumnMapping());
    }*/
}
