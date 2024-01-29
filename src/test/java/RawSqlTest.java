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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testInsert() {
        String sql = """
            INSERT INTO books (isbn, title, synopsis, publisher_id, price, cover)  
            VALUES (?,?,?,?,?,?)
        """;
        RawSql.statement(sql, List.of("1111111111111", "prueba título", "prueba sinopsis", 1, new BigDecimal(23.45), "imagen.jpeg"));
        sql = "SELECT * FROM books WHERE isbn = ?";
        try (ResultSet resultSet = RawSql.select(sql, List.of("1111111111111"))){
            if(resultSet.next()) {
                assertEquals("prueba título", resultSet.getString("title"));
            } else {
                fail("No se encuentra el libro");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
    }

}
