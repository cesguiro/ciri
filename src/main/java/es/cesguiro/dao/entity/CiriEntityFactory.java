package es.cesguiro.dao.entity;

public interface CiriEntityFactory<T extends CiriEntity> {
    T createEntity();
}
