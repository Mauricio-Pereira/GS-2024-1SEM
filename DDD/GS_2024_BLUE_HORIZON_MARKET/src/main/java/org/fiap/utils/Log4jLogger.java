package org.fiap.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fiap.entities._BaseEntity;

public class Log4jLogger<T extends _BaseEntity> {

    private final Logger logger;

    public Log4jLogger(Class<T> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    public void logCreate(T entity) {
        logger.info("Create: " + entity);
    }

    public void logReadById(T entity) {
        logger.info("Read by ID: " + entity);
    }

    public void logReadAll() {
        logger.info("Read all entities");
    }

    public void logUpdateById(T entity) {
        logger.info("Update: " + entity);
    }

    public void logDeleteById(T entity) {
        logger.info("Delete by ID: " + entity);
    }
}
