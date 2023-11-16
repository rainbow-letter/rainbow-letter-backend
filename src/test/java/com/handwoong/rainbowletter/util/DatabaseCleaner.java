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
    private List<String> tables;

    @Override
    public void afterPropertiesSet() {
        tables = entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> entityType.getName().toLowerCase(Locale.ROOT))
                .toList();
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        for (final String table : tables) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE " + table + " ALTER COLUMN id RESTART WITH 1").executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
