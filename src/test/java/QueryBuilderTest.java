import es.cesguiro.AppPropertiesReader;
import es.cesguiro.queryBuilder.DB;
import es.cesguiro.rawSql.RawSql;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
public class QueryBuilderTest {

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
    public void testSelectAllWithAllFields() {
        try(ResultSet resultSet = DB.table("books").get()) {
            if(resultSet.next()) {
                assertEquals("9788433920423", resultSet.getString("isbn"));
            }
            else {
                log.warn("No hay resultados en la tabla books");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectAllWithOnlyTwoFields() {
        try(ResultSet resultSet = DB.table("books").select("isbn", "title").get()) {
            assertAll(
                    () -> assertTrue(resultSet.next(), "Se esperaba al menos un resultado en la tabla books"),
                    () -> {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        assertAll(
                                () -> assertEquals("9788433920423", resultSet.getString("isbn")),
                                () -> assertEquals(2, columnCount, "Se esperaba exactamente dos columnas"),
                                () -> assertEquals("isbn", metaData.getColumnName(1).toLowerCase(), "El nombre de la primera columna debería ser 'isbn'"),
                                () -> assertEquals("title", metaData.getColumnName(2).toLowerCase(), "El nombre de la segunda columna debería ser 'title'")
                        );
                    }
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectAllWithWhereClausule() {
        try(ResultSet resultSet = DB
                .table("books")
                .where("title", "=", "'El nombre de la rosa'")
                .get()) {
            if(resultSet.next()) {
                assertEquals("9788426418197", resultSet.getString("isbn"));
            }
            else {
                log.warn("No hay resultados en la tabla books");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectWithWhereClausulePrice() {
        try(ResultSet resultSet = DB
                .table("books")
                .where("price", "<", "10.0")
                .get()) {
            if(resultSet.next()) {
                assertEquals("9788448022440", resultSet.getString("isbn"));
            }
            else {
                log.warn("No hay resultados en la tabla books");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectWithAndWhereClausule() {
        try(ResultSet resultSet = DB
                .table("books")
                .where("price", "<", "10.0")
                .andWhere("publisher_id", "=", "4")
                .get()) {
            if(resultSet.next()) {
                assertEquals("9788499085944", resultSet.getString("isbn"));
            }
            else {
                log.warn("No hay resultados en la tabla books");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectWithOrWhereClausule() {
        try(ResultSet resultSet = DB
                .table("books")
                .where("price", "<", "10.0")
                .orWhere("publisher_id", "=", "4")
                .get()) {
            if(resultSet.next()) {
                assertEquals("9788466338141", resultSet.getString("isbn"));
            }
            else {
                log.warn("No hay resultados en la tabla books");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFindByIsbn(){
        try(ResultSet resultSet = DB.table("books").find("9788433927996")) {
            assertEquals("9788433927996", resultSet.getString("isbn"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testFindByNonExistingIsbn(){
        try(ResultSet resultSet = DB.table("books").find("9")) {
            assertEquals(null, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDelete() {
        int rowsAfected = DB.table("books")
                .where("isbn", "=", "9788496173729")
                .delete();
        try(ResultSet resultSet = DB.table("books").find("9788496173729")) {
            assertAll(
                    () -> {
                        assertAll(
                                () -> assertEquals(1, rowsAfected),
                                () -> assertEquals(null, resultSet)
                        );
                    }
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, rowsAfected);
    }

    @Test
    public void testUpdate() {
        String title = "Título cambiado";
        float price = 23.40f;
        Map<String, Object> parameters= Map.of(
                "title", title,
                "price", price
        );
        int rowsAfected = DB.table("books")
                .where("isbn", "=", "9788496173729")
                .update(parameters);
        try(ResultSet resultSet = DB.table("books").find("9788496173729")) {
            assertAll(
                    () -> {
                        assertAll(
                                () -> assertEquals(1, rowsAfected),
                                () -> assertEquals(title, resultSet.getString("title")),
                                () -> assertEquals(price, resultSet.getFloat("price"))
                        );
                    }
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testInsert() {
        String isbn = "1111111111111";
        String title = "Título nuevo";
        String synopsis = "Sinonpsis nueva";
        int publisher_id = 2;
        float price = 23.40f;
        String cover = "imagenNueva.jpeg";
        Map<String, Object> parameters= Map.of(
                "isbn", isbn,
                "title", title,
                "synopsis", synopsis,
                "publisher_id", publisher_id,
                "price", price,
                "cover", cover
        );
        int rowsAfected = DB.table("books")
                .insert(parameters);
        try(ResultSet resultSet = DB.table("books").find(isbn)) {
            assertAll(
                    () -> {
                        assertAll(
                                () -> assertEquals(1, rowsAfected),
                                () -> assertEquals(title, resultSet.getString("title")),
                                () -> assertEquals(synopsis, resultSet.getString("synopsis")),
                                () -> assertEquals(publisher_id, resultSet.getInt("publisher_id")),
                                () -> assertEquals(price, resultSet.getFloat("price")),
                                () -> assertEquals(cover, resultSet.getString("cover"))
                        );
                    }
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectAllOrderByTitleAsc() {
        try(ResultSet resultSet = DB.table("books").orderBy("title", "ASC").get()) {
            if(resultSet.next()) {
                assertEquals("9788415729204", resultSet.getString("isbn"));
            }
            else {
                log.warn("No hay resultados en la tabla books");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectAllOrderByTitleDesc() {
        try(ResultSet resultSet = DB.table("books").orderBy("title", "DESC").get()) {
            if(resultSet.next()) {
                assertEquals("9788496173101", resultSet.getString("isbn"));
            }
            else {
                log.warn("No hay resultados en la tabla books");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectWithLimit() {
        int limit = 3;
        List<Object> rows = new ArrayList<>();
        try(ResultSet resultSet = DB.table("books").limit(limit, null).get()) {
            while (resultSet.next()) {
                rows.add(resultSet.getString("isbn"));
            }
            assertAll(
                    () -> {
                        assertAll(
                                () -> assertEquals(limit, rows.size()),
                                () -> assertEquals("9788433920423", rows.get(0)),
                                () -> assertEquals("9788426418197", rows.get(1)),
                                () -> assertEquals("9786074213485", rows.get(2))
                        );
                    }
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectWithLimitAndOffset() {
        int limit = 3;
        int offset = 4;
        List<Object> rows = new ArrayList<>();
        try(ResultSet resultSet = DB.table("books").limit(limit, offset).get()) {
            while (resultSet.next()) {
                rows.add(resultSet.getString("isbn"));
            }
            assertAll(
                    () -> {
                        assertAll(
                                () -> assertEquals(offset, rows.size()),
                                () -> assertEquals("9788466338141", rows.get(0)),
                                () -> assertEquals("9788448022440", rows.get(1)),
                                () -> assertEquals("9788499085944", rows.get(2)),
                                () -> assertEquals("9788499086460", rows.get(3))
                        );
                    }
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBooksJoinPublishers() {
        try(ResultSet resultSet = DB.table("books")
                .join("publishers", "id", "publisher_id")
                .select("books.*", "publishers.name").get()) {
            if(resultSet.next()) {
                assertAll(
                        () -> {
                            assertAll(
                                    () -> assertEquals("9788433920423", resultSet.getString("books.isbn")),
                                    () -> assertEquals("Editorial Anagrama", resultSet.getString("publishers.name"))
                            );
                        }
                );
            } else {
                log.warn("No hay resultados en la tabla books");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
