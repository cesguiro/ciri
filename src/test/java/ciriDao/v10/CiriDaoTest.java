package ciriDao.v10;

import ciriDao.v10.dao.BookCiriDao;
import ciriDao.v10.dao.OrderCiriDao;
import ciriDao.v10.entity.BookEntity;
import ciriDao.v10.entity.OrderEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


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
        assertEquals("9788426418197", bookEntityList.get(1).getIsbn());
    }

    @Test
    public  void testFindBookById() {
        Optional<BookEntity> bookEntity = bookCiriDao.findById("9786074213485");
        assertEquals("La insorportable levedad del ser", bookEntity.get().getTitle());
    }

    @Test
    public  void testFindPublisherById() {
        Optional<OrderEntity> orderEntity = orderCiriDao.findById(1);

        // Convertir java.sql.Date a un String formateado
        java.sql.Date orderDateFromEntity = orderEntity.get().getOrderDate();
        String formattedOrderDate = new SimpleDateFormat("yyyy-MM-dd").format(orderDateFromEntity);

        assertEquals("2022-01-15", formattedOrderDate);
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
    }
}
