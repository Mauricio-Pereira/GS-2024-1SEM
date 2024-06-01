package org.fiap.repositories;

import org.fiap.entities._BaseEntity;

import java.util.List;

public interface _BaseRepository<T extends _BaseEntity> {
    public void create(T entity);
    public List<T> readAll();
    public boolean deleteById(int id);
    public boolean updateById(T entity, int id);
    public T readById(int id);
}
