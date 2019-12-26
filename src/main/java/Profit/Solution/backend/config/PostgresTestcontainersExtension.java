package Profit.Solution.backend.config;

import org.hibernate.dialect.PostgreSQL9Dialect;
import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestcontainersExtension implements Extension {

    static {
        System.out.println("Postgres testcontainers extension starting...\n");

        PostgreSQLContainer postgres = new PostgreSQLContainer();
        postgres.start();

        System.setProperty("spring.datasource.driver-class-name", postgres.getDriverClassName());
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
        System.setProperty("spring.jpa.properties.hibernate.dialect", PostgreSQL9Dialect.class.getCanonicalName());
    }
}