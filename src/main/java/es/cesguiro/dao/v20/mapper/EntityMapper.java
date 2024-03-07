package es.cesguiro.dao.v20.mapper;

import es.cesguiro.dao.v20.entity.CiriEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityMapper<T extends CiriEntity> {

    public abstract T toEntity(ResultSet resultSet);
    public List<T> toEntityList(ResultSet resultSet) {
        List<T> entityList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T entity = toEntity(resultSet);
                entityList.add(entity);
            }
            return entityList;
        }catch (SQLException e) {
            throw new RuntimeException("Error while converting ResultSet to entity list", e);
        }
    }
}
