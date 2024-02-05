package ciriDao.v10;

import ciriDao.v10.dao.BookCiriDao;
import ciriDao.v10.dao.PublisherCiriDao;
import ciriDao.v10.entity.AuthorEntity;
import ciriDao.v10.entity.BookEntity;
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



    BookCiriDao bookCiriDao = new BookCiriDao();
    PublisherCiriDao publisherCiriDao = new PublisherCiriDao();
    //OrderCiriDao orderCiriDao = new OrderCiriDao();

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
    public void testFindAllWithAuthorList() {
        List<BookEntity> bookEntityList = bookCiriDao.findAll();
        PublisherEntity publisherEntity = new PublisherEntity(
                5,
                "Minotauro"
        );

        List<AuthorEntity> authorEntityList = List.of(
                new AuthorEntity(4, "Terry Pratchett", "Británico", 1948, 2015),
                new AuthorEntity(5, "Neil Gaiman", "Británico", 1960, null)
        );
        int expectedSize = 12;
        assertAll( () -> {
            assertEquals(expectedSize, bookEntityList.size());
            assertEquals("9788448022440", bookEntityList.get(4).getIsbn());
            assertEquals(publisherEntity, bookEntityList.get(4).getPublisherEntity());
            assertEquals(authorEntityList.get(0), bookEntityList.get(4).getAuthorEntityList().get(0));
            assertEquals(authorEntityList.get(1), bookEntityList.get(4).getAuthorEntityList().get(1));
        });
    }

    @Test
    public void testOneToMany(){
        int id = 2;
        PublisherEntity publisherEntity = publisherCiriDao.findById(id).get();
        List<BookEntity> bookEntityList = List.of(
                new BookEntity(2,
                        "9788426418197",
                        "El nombre de la rosa",
                        "Valiéndose de las características de la novela gótica, la crónica medieval y la novela policíaca, El nombre de la rosa narra las investigaciones detectivescas que realiza el fraile franciscano Guillermo de Baskerville para esclarecer los crímenes cometidos en una abadía benedictina en el año 1327. Le ayudará en su labor el novicio Adso, un joven que se enfrenta por primera vez a las realidades de la vida situadas más allá de las puertas del convento.",
                        new BigDecimal(12.30),
                        "nombreRosa.jpeg"),
                new BookEntity(7,
                        "9788499086460",
                        "Mort",
                        "Mort es una novela de fantasía escrita por Terry Pratchett y publicada en 1987. Es la cuarta entrega de la saga del Mundodisco. En ella, la Muerte, presente en todas las novelas de la saga, desempeña por primera vez un papel protagonista. Ha sido adaptada como obra de teatro.​",
                        new BigDecimal(10.40),
                        "mort.jpeg")
        );
        int expectedSize = 2;
        assertAll( () -> {
            assertEquals("Penguin Random House Grupo Editorial España", publisherEntity.getName());
            assertEquals(expectedSize, publisherEntity.getBookEntityList().size());
            assertEquals("9788426418197", publisherEntity.getBookEntityList().get(0).getIsbn());
            assertEquals("9788499086460", publisherEntity.getBookEntityList().get(1).getIsbn());
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
