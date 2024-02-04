package es.cesguiro.dao.v40.entity;

public interface CiriEntityFactory<T extends CiriEntity> {
    T createEntity();
}
