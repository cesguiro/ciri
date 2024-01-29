package es.cesguiro.queryBuilder;

import es.cesguiro.rawSql.RawSql;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DB {

    private final String tableName;

    @Getter
    private StringBuilder sql;

    @Getter
    private List<Object> parameters = new ArrayList<>();

    private DB(String tableName) {
        //sólo permitir instancias a través del método table()
        this.tableName = tableName;
        this.sql =  new StringBuilder("SELECT * FROM " + tableName);
    }

    public static DB table(String tableName) {
        return new DB(tableName);
    }

    public DB select(String... fields) {
        if (fields.length > 0) {
            this.sql.replace(this.sql.indexOf("*"), this.sql.indexOf("FROM") - 1, String.join(", ", fields));
        }
        return this;
    }

    public DB where(String field, String operator, Object value) {
        if(value != null) {
            this.sql.append(" WHERE ").append(field).append(operator).append("?");
            this.parameters.add(value);
        }
        return this;
    }

    public DB andWhere(String field, String operator, Object value) {
        if (value != null) {
            this.sql.append(" AND ").append(field).append(operator).append(" ?");
            this.parameters.add(value);
        }
        return this;
    }

    public DB orWhere(String field, String operator, Object value) {
        if (value != null) {
            this.sql.append(" OR ").append(field).append(" ").append(operator).append(" ?");
            this.parameters.add(value);
        }
        return this;
    }

    public ResultSet find(Object id) {
        this.sql.append(" WHERE isbn = ?");
        this.parameters.add(id);
        ResultSet resultSet = this.get();
        try {
            if(resultSet.next()) {
                return resultSet;
            }
            return null;
        } catch (SQLException e) {
            throw  new RuntimeException("Error al recuperar el recurso", e);
        }
    }

    public DB orderBy(String field, String direction) {
        if (field != null && !field.isEmpty()) {
            this.sql.append(" ORDER BY ").append(field);
            if (direction != null && !direction.isEmpty()) {
                this.sql.append(" ").append(direction);
            }
        }
        return this;
    }

    public DB join(String referencedTable, String primaryKey, String foreignKey) {
        this.sql
                .append(" INNER JOIN ")
                .append(referencedTable)
                .append(" ON ")
                .append(referencedTable)
                .append(".")
                .append(primaryKey)
                .append("=")
                .append(this.tableName)
                .append(".")
                .append(foreignKey);
        return this;
    }

    public DB limit(int limit, Integer offset) {
        this.sql.append(" LIMIT ?");
        this.parameters.add(limit);
        if (offset != null) {
            this.sql.append(",?");
            this.parameters.add(offset);
        }
        return this;
    }

    public int insert(Map<String, Object> values) {
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(this.tableName).append(" (");
        StringBuilder placeholders = new StringBuilder(") VALUES (");
        List<Object> parameters = new ArrayList<>();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            sql.append(entry.getKey()).append(",");
            placeholders.append("?,");
            parameters.add(entry.getValue());
        }

        sql.deleteCharAt(sql.length() - 1);
        placeholders.deleteCharAt(placeholders.length() - 1);

        sql.append(placeholders).append(")");

        return RawSql.statement(sql.toString(), parameters);
    }

    public int update(Map<String, Object> values) {
        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        List<Object> parameters = new ArrayList<>();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            sql.append(entry.getKey()).append("=?,");
            parameters.add(entry.getValue());
        }

        sql.deleteCharAt(sql.length() - 1);

        if(this.sql.toString().contains("WHERE")) {
            String[] parts = this.sql.toString().split("WHERE", 2);
            String whereClause = parts[1].trim();
            sql.append(" WHERE ").append(whereClause);
            parameters.addAll(this.parameters);
        }
        return RawSql.statement(sql.toString(), parameters);
    }

    public int delete() {
        this.sql.replace(this.sql.indexOf("SELECT * "), this.sql.indexOf("FROM"), ("DELETE "));
        return RawSql.statement(sql.toString(), this.parameters);
    }

    public ResultSet get() {
        return RawSql.select(this.sql.toString(), this.parameters);
    }
}
