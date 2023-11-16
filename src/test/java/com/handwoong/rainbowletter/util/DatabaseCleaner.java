package com.handwoong.rainbowletter.util;

import java.util.List;
import java.util.Locale;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner implements InitializingBean {
    @Autowired
    private EntityManager entityManager;
    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> entityType.getName().toLowerCase(Locale.ROOT))
                .toList();
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        for (final String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            entityManager
                    .createNativeQuery(
                            "ALTER TABLE " + tableName + " ALTER COLUMN " + tableName + "_id" + " RESTART WITH 1")
                    .executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
