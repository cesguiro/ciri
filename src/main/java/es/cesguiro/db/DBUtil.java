package es.cesguiro.db;

import java.sql.*;
import java.util.List;

public class DBUtil {

    private final DBConnection dBConnection;

    public DBUtil() {
       this.dBConnection = new DBConnection();
   }

    public ResultSet select(String sql, List<Object> values) {
        try {
            PreparedStatement preparedStatement = setParameters(sql, values);
            return preparedStatement.executeQuery();            
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar la sentencia: " + sql);
        }
    }

    public int insert(String sql, List<Object> values) {
        try {
            PreparedStatement preparedStatement = setParameters(sql, values);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                return resultSet.getInt(1);
            } else {
                throw new RuntimeException("No se puede leer el último id generado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int update(String sql, List<Object> values) {
        try {
            PreparedStatement preparedStatement = setParameters(sql, values);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(String sql, List<Object> values) {
        try {
            PreparedStatement preparedStatement = setParameters(sql, values);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement setParameters(String sql, List<Object> values){
        //Connection connection = dBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = dBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if(values != null) {
                for(int i=0;i<values.size();i++) {
                    Object value = values.get(i);
                    preparedStatement.setObject(i+1,value);
                }
            }    
            return preparedStatement;                        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
