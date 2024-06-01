package org.fiap.repositories;

import org.fiap.entities._BaseEntity;
import org.fiap.utils.Log4jLogger;

import java.util.List;

public class _BaseRepositoryImpl<T extends _BaseEntity> implements _BaseRepository<T> {

    protected Log4jLogger<T> logger;

    public _BaseRepositoryImpl(Class<T> tClass) {
        this.logger = new Log4jLogger<>(tClass);
    }

    @Override
    public void create(T entity) {
        // Código para criar a entidade
        logger.logCreate(entity);
    }

    @Override
    public List<T> readAll() {
        // Código para ler todas as entidades
        logger.logReadAll();
        return null; // Retornar a lista de entidades
    }

    @Override
    public boolean deleteById(int id) {
        // Código para deletar a entidade
        logger.logDeleteById(readById(id));
        return false; // Retornar o resultado da operação
    }

    @Override
    public boolean updateById(T entity, int id) {
        // Código para atualizar a entidade
        logger.logUpdateById(entity);
        return false; // Retornar o resultado da operação
    }

    @Override
    public T readById(int id) {
        // Código para ler a entidade por ID
        logger.logReadById(readById(id));
        return null; // Retornar a entidade
    }
}
