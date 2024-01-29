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
    public void testDete() {
        int rowsAfected = DB.table("books").where("isbn", "=", "9788496173729").delete();

    }

}
