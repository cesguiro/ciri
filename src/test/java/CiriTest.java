import dao.BookDao;
import entity.BookEntity;
import es.cesguiro.AppPropertiesReader;
import es.cesguiro.rawSql.RawSql;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Log4j2
public class CiriTest {


    //BookFactory bookFactory = new BookFactory();

    BookDao bookDao = new BookDao();

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
        bookDao.findAll();
        /*List<BookEntity> bookEntityList = bookDao.findAll();
        assertEquals("9788426418197", bookEntityList.get(1).getIsbn().getValue());*/
    }

    @Test
    public  void testFindById() {
        BookEntity bookEntity = bookDao.findById("9786074213485");

        assertEquals("La insorportable levedad del ser", bookEntity.getTitle().getValue());
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

        bookDao.save(bookEntity);
    }*/
}