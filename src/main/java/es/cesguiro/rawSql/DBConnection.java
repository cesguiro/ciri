package es.cesguiro.rawSql;

import es.cesguiro.common.AppPropertiesReader;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
@Getter
public class DBConnection {

    private final Connection connection;

    public DBConnection() {
        log.info("Estableciendo conexión....");
        try {
            connection = DriverManager.getConnection(
                    AppPropertiesReader.getProperty("ciri.datasource.url"),
                    AppPropertiesReader.getProperty("ciri.datasource.username"),
                    AppPropertiesReader.getProperty("ciri.datasource.password")
            );
            String autocommitPropertyValue = AppPropertiesReader.getProperty("ciri.autocommit");
            if(autocommitPropertyValue != null) {
                boolean autocommitValue = Boolean.parseBoolean(autocommitPropertyValue);
                connection.setAutoCommit(autocommitValue);
            }
            //log.warn("Conexión establecida con la bbdd");
            log.info("Conexión establecida con la bbdd");
        } catch (SQLException e) {
            log.error("Error al conectar con la bbdd . Parámetros: " + getParameters());
            throw new RuntimeException("Connection paramaters :\n\n" + getParameters() + "\nOriginal exception message: " + e.getMessage());
        }
    }


    private String getParameters (){
        return String.format("url: %s\nUser: %s\nPassword: %s\n",
                AppPropertiesReader.getProperty("ciri.datasource.url"),
                AppPropertiesReader.getProperty("ciri.datasource.username"),
                AppPropertiesReader.getProperty("ciri.datasource.password")
        );
    }

}
