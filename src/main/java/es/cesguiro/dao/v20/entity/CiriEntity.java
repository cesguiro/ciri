package es.cesguiro.dao.v20.entity;

import es.cesguiro.common.annotations.Column;
import es.cesguiro.common.annotations.Id;
import es.cesguiro.common.annotations.TableName;
import es.cesguiro.dao.v20.mapper.EntityMapper;
import es.cesguiro.queryBuilder.DB;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Getter
@ToString
public abstract class CiriEntity {

    public String getTableName() {
        TableName tableNameAnnotation = getClass().getAnnotation(TableName.class);
        if(tableNameAnnotation != null) {
            return tableNameAnnotation.value();
        }
        throw new RuntimeException("@TableName annotation does not exist in the entity");
    }

    private String getDBFieldName(Field field) {
        String fieldName = field.getName();
        if (field.isAnnotationPresent(Column.class)) {
            fieldName = field.getAnnotation(Column.class).value();
        }
        return fieldName;
    }

    public String getIdDBFieldName() {
        Field[] fields = getClass().getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Id.class)) {
                return this.getDBFieldName(field);
            }
        }
        throw new RuntimeException("No field annotated with @Id found in the entity");
    }

    public Object getIdValue() {
        Field[] fields = getClass().getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true); // Necesario si el campo es privado
                try {
                    return field.get(this);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing the value of the field annotated with @Id", e);
                }
            }
        }
        throw new RuntimeException("No field annotated with @Id found in the entity");
    }

    /*public Map<String, String> getJavaToDBColumnMapping() {
        Map<String, String> fieldMap = new HashMap<>();
        Field[] fields = getClass().getDeclaredFields();

        for(Field field : fields) {
            String attributeName = field.getName();
            String dbFieldName = this.getDBFieldName(field);

            fieldMap.put(attributeName, dbFieldName);
        }
        return fieldMap;
    }*/

    public <T extends CiriEntity> Optional<T> manyToOne(
            Class<T> entityType,
            EntityMapper entityMapper
    ) {
        T entity = null;
        try {
                entity = entityType.getDeclaredConstructor().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        /*ResultSet resultSet = DB.table(entity.getTableName())
                .where(entity.getIdDBFieldName(), "=", getIdValue())
                .get();*/
        try (ResultSet resultSet = DB.table(entity.getTableName())
                .where(entity.getIdDBFieldName(), "=", getIdValue())
                .get()){
            if(!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of((T) entityMapper.toEntity(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Error while converting ResultSet to entity", e);
        }
    }

    public List<CiriEntity> manyToMany(
            String referencedTable,
            String joinTable,
            String joinColumn,
            String referencedId,
            String inverseJoinColumn,
            Object idValue,
            EntityMapper entityMapper) {
        ResultSet resultSet = DB
                .table(referencedTable)
                .select(referencedTable + ".*")
                .join(joinTable, joinTable + "." + joinColumn, referencedTable + "." + referencedId)
                .join(this.getTableName(), this.getTableName() + "." + this.getIdDBFieldName(), joinTable + "." + inverseJoinColumn)
                .where(this.getTableName() + "." + this.getIdDBFieldName(), "=", idValue)
                .get();
        return entityMapper.toEntityList(resultSet);
    }

    public List<CiriEntity> oneToMany(String referencedTable, String referencedForeginKey, Object foreignKeyValue, EntityMapper entityMapper) {
        ResultSet resultSet = DB
                .table(referencedTable)
                .where(referencedForeginKey, "=", foreignKeyValue)
                .get();
        return entityMapper.toEntityList(resultSet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CiriEntity that = (CiriEntity) o;
        return Objects.equals(this.getIdDBFieldName(), that.getIdDBFieldName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getIdDBFieldName());
    }
}
