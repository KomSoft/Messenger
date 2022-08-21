package com.itea.messenger;

import lombok.extern.java.Log;
import org.testcontainers.containers.PostgreSQLContainer;

@Log
public class ITEAPostgreSQLContainer extends PostgreSQLContainer<ITEAPostgreSQLContainer> {
    private static ITEAPostgreSQLContainer container;

    private ITEAPostgreSQLContainer() {
        super("postgres:13");
    }

    public static ITEAPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new ITEAPostgreSQLContainer()
                    .withDatabaseName("messenger_test")
                    .withUsername("postgres_test").withPassword("password");
//                    .withInitScript("initMessages.sql");
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DRIVER_CLASS_NAME", "org.postgresql.Driver");
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
        log.info("Testcontainer '" + container.getDatabaseName() + "' started with DB_ULR=" + container.getJdbcUrl() + ", DB_USERNAME= " + container.getUsername() + ", DB_PASSWORD=" + container.getPassword());
    }

    @Override
    public void stop() {
    }

}
