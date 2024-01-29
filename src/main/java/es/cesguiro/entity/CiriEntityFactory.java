package es.cesguiro.entity;

public interface CiriEntityFactory<T extends CiriEntity> {
    T createEntity();
}
