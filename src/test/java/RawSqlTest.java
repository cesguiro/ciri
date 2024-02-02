import es.cesguiro.AppPropertiesReader;
import es.cesguiro.rawSql.RawSql;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RawSqlTest {

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
    @Order(1)
    public void testSelectAll() {
        final String sql = "SELECT * FROM books";
        try (ResultSet resultSet = RawSql.select(sql, null)){
            if(resultSet.next()) {
                assertEquals("9788433920423", resultSet.getString("isbn"));
            }
            else {
                log.warn("No hay resultados: " + sql);
            }
        } catch (SQLException e) {
            fail("Error executing SQL query: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void testSelectByIsbn() {
        final String sql = "SELECT * FROM books WHERE isbn = ?";
            try (ResultSet resultSet = RawSql.select(sql, List.of("9788499086460"))){
                if (resultSet.next()) {
                    assertEquals("Mort", resultSet.getString("title"));
                } else {
                    fail("No se encuentra el libro");
                }
        } catch (SQLException e) {
                fail("Error executing SQL query: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testInsertWithoutId() {
        String isbn = "1111111111111";
        String title = "prueba título";
        String synopsis = "prueba synopsis";
        int publisher_id = 1;
        float price = 23.45f;
        String cover = "imagen.jpeg";
        String sql = """
            INSERT INTO books (isbn, title, synopsis, publisher_id, price, cover)  
            VALUES (?,?,?,?,?,?)
        """;
        int lastId = (int) RawSql.insert(sql, List.of(
                isbn,
                title,
                synopsis,
                publisher_id,
                price,
                cover));
        sql = "SELECT * FROM books WHERE isbn = ?";
        try (ResultSet resultSet = RawSql.select(sql, List.of(isbn))){
            if(resultSet.next()) {
                assertAll(
                        () -> {
                            assertAll(
                                    () -> assertEquals(13, lastId),
                                    () -> assertEquals(title, resultSet.getString("title")),
                                    () -> assertEquals(synopsis, resultSet.getString("synopsis")),
                                    () -> assertEquals(publisher_id, resultSet.getInt("publisher_id")),
                                    () -> assertEquals(price, resultSet.getFloat("price")),
                                    () -> assertEquals(cover, resultSet.getString("cover"))
                            );
                        }
                );

            } else {
                fail("No se encuentra el libro");
            }
        } catch (SQLException e) {
            fail("Error executing SQL query: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    public void testSelectByIsbnTestRollback() {
        final String sql = "SELECT * FROM books WHERE isbn = ?";
        try (ResultSet resultSet = RawSql.select(sql, List.of("1111111111111"))){
            if(resultSet.next()) {
                fail("No se ha realizado el rollback después del test");
            }
        } catch (SQLException e) {
            fail("Error executing SQL query: " + e.getMessage());
        }
    }

    @Test
    public void testInsertWithId() {
        String isbn = "1111111111111";
        String title = "prueba título";
        String sql = """
                INSERT INTO booksWithoutId (isbn, title)  
                VALUES (?,?)
            """;
        Object lastId = RawSql.insert(sql, List.of(isbn, title));
        sql = "SELECT * FROM booksWithoutId WHERE isbn = ?";
            try (ResultSet resultSet = RawSql.select(sql, List.of(isbn))){
            if(resultSet.next()) {
                assertAll(
                        () -> {
                            assertAll(
                                    () -> assertEquals(isbn, lastId),
                                    () -> assertEquals(title, resultSet.getString("title"))
                            );
                        }
                );
            } else {
                fail("No se encuentra el libro");
            }
        } catch (SQLException e) {
                fail("Error executing SQL query: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateOneRecord() {
        String isbn = "9788433920423";
        String title = "título modificado";
        String sql = """
                UPDATE books set title = ? WHERE isbn = ?  
            """;
        int rowsAffected = RawSql.update(sql, List.of(title, isbn));
        sql = "SELECT * FROM books WHERE isbn = ?";
        try (ResultSet resultSet = RawSql.select(sql, List.of(isbn))){
            if(resultSet.next()) {
                assertAll(
                        () -> {
                            assertAll(
                                    () -> assertEquals(1, rowsAffected),
                                    () -> assertEquals(title, resultSet.getString("title"))
                            );
                        }
                );
            } else {
                fail("No se encuentra el libro");
            }
        } catch (SQLException e) {
            fail("Error executing SQL query: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateMoreThanOneRecord() {
        Integer publisherId = 2;
        String title = "título modificado";
        String sql = """
                UPDATE books set title = ? WHERE publisher_id = ?  
            """;
        int rowsAffected = RawSql.update(sql, List.of(title, publisherId));
        assertEquals(2, rowsAffected);
    }


    @Test
    public void deleteOneRecord() {
        int id = 1;
        String sql = """
                DELETE FROM authors WHERE id = ?  
            """;
        int rowsAffected = RawSql.update(sql, List.of(id));
        sql = "SELECT * FROM authors WHERE id = ?";
        try (ResultSet resultSet = RawSql.select(sql, List.of(id))){
            assertAll(
                    () -> assertEquals(1, rowsAffected),
                    () -> assertFalse(resultSet.next())
            );
        } catch (SQLException e) {
            fail("Error executing SQL query: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteMoreThanOneRecord() {
        Integer birthYear = 1948;
        String sql = """
                DELETE FROM authors WHERE birth_year = ?  
            """;
        int rowsAffected = RawSql.update(sql, List.of(birthYear));
        assertEquals(2, rowsAffected);
    }

}
