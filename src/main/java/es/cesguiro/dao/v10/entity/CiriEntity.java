package es.cesguiro.dao.v10.entity;

import es.cesguiro.dao.v10.mapper.EntityMapper;
import es.cesguiro.queryBuilder.DB;
import lombok.Getter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Getter
@ToString
public abstract class CiriEntity {
    
    public abstract String getTableName();

    public abstract String getIdDBFieldName();

    public abstract Object getIdValue();

    public abstract Map<String, String> getJavaToDBColumnMapping();

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


    public Optional<CiriEntity> manyToOne(String referencedTable, String referencedPrimaryKey, Object primaryKeyValue, EntityMapper entityMapper) {
        ResultSet resultSet = DB.table(referencedTable)
                .where(referencedPrimaryKey, "=", primaryKeyValue)
                .get();
        try {
            if(!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(entityMapper.toEntity(resultSet));
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
}
