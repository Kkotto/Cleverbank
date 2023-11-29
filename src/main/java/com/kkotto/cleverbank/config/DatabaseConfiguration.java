package com.kkotto.cleverbank.config;

import com.kkotto.cleverbank.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConfiguration {
    private static DatabaseConfiguration instance;

    public static DatabaseConfiguration getInstance() {
        if (instance == null) {
            instance = new DatabaseConfiguration();
        }
        return instance;
    }

    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private static final String JDBC_DRIVER_NAME_KEY = "jdbc.driver.name";
    private static final String JDBC_URL_KEY = "jdbc.url";
    private static final String JDBC_USER_KEY = "jdbc.user";
    private static final String JDBC_PASSWORD_KEY = "jdbc.password";
    private static final String JDBC_DATABASE_KEY = "jdbc.database";
    private static String JDBC_URL_VALUE;
    private static String JDBC_USER_VALUE;
    private static String JDBC_PASSWORD_VALUE;
    private static String JDBC_DRIVER_NAME_VALUE;
    private static String JDBC_DATABASE_NAME_VALUE;

    private static final Logger logger = LogManager.getLogger(DatabaseConfiguration.class);

    private DatabaseConfiguration() {
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseConfiguration.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            properties.load(inputStream);
            JDBC_DRIVER_NAME_VALUE = properties.getProperty(JDBC_DRIVER_NAME_KEY);
            JDBC_URL_VALUE = properties.getProperty(JDBC_URL_KEY);
            JDBC_USER_VALUE = properties.getProperty(JDBC_USER_KEY);
            JDBC_PASSWORD_VALUE = properties.getProperty(JDBC_PASSWORD_KEY);
            JDBC_DATABASE_NAME_VALUE = properties.getProperty(JDBC_DATABASE_KEY);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public Connection getConnection() throws DatabaseException {
        try {
            Class.forName(JDBC_DRIVER_NAME_VALUE);
            createDatabaseIfNotExists();
            return DriverManager.getConnection(JDBC_URL_VALUE + JDBC_DATABASE_NAME_VALUE, JDBC_USER_VALUE, JDBC_PASSWORD_VALUE);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private void createDatabaseIfNotExists() {
        // В PostgreSQL нельзя использовать параметры (заполнители) для имени базы данных или идентификаторов
        // объектов (например, таблиц и столбцов) в SQL-запросах.
        // Невозможно использовать PreparedStatement для создания БД
        String createDatabaseSQL = "CREATE DATABASE " + JDBC_DATABASE_NAME_VALUE;
        try (Connection connection = DriverManager.getConnection(JDBC_URL_VALUE, JDBC_USER_VALUE, JDBC_PASSWORD_VALUE);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createDatabaseSQL);
            logger.warn(String.format("%s was created", JDBC_DATABASE_NAME_VALUE));
        } catch (SQLException e) {
            logger.warn(String.format("Check if %s already exists", JDBC_DATABASE_NAME_VALUE));
            logger.warn(e.getMessage());
        }
    }
}
