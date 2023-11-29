package com.kkotto.cleverbank.config;

import com.kkotto.cleverbank.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TablesConfiguration {
    private static TablesConfiguration instance;
    private static final Logger logger = LogManager.getLogger(TablesConfiguration.class);

    public static TablesConfiguration getInstance(Connection connection) {
        if (instance == null) {
            instance = new TablesConfiguration(connection);
        }
        return instance;
    }

    private final Connection connection;

    private TablesConfiguration(Connection connection) {
        this.connection = connection;
    }

    public void createTables() throws DatabaseException {
        createBankTable();
        createCustomerTable();
        createAccountTable();
        createTransactionTable();
    }

    private void createBankTable() throws DatabaseException {
        try (Statement statement = this.connection.createStatement()) {
            String sql = """
                        CREATE TABLE IF NOT EXISTS bank (
                             id SERIAL,
                             name VARCHAR(255) NOT NULL UNIQUE,
                             CONSTRAINT bank_pk PRIMARY KEY (id)
                         );
                    """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("Bank table creation failed!");
            throw new DatabaseException(e.getMessage());
        }
    }

    private void createCustomerTable() throws DatabaseException {
        try (Statement statement = this.connection.createStatement()) {
            String sql = """
                        CREATE TABLE IF NOT EXISTS customer (
                             id SERIAL,
                             full_name VARCHAR(255) NOT NULL UNIQUE,
                             email VARCHAR(255) UNIQUE,
                             phone_number VARCHAR(255) UNIQUE,
                             CONSTRAINT customer_pk PRIMARY KEY (id)
                         );
                    """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("Customer table creation failed!");
            throw new DatabaseException(e.getMessage());
        }
    }

    private void createAccountTable() throws DatabaseException {
        try (Statement statement = this.connection.createStatement()) {
            String sql = """
                        CREATE TABLE IF NOT EXISTS account (
                               id SERIAL,
                               customer_id INT,
                               bank_id INT,
                               balance DECIMAL(10, 2),
                               CONSTRAINT account_pk PRIMARY KEY (id),
                               CONSTRAINT customer_fk FOREIGN KEY (customer_id) REFERENCES customer (id),
                               CONSTRAINT bank_fk FOREIGN KEY (bank_id) REFERENCES bank (id)
                           );
                    """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("Account table creation failed!");
            throw new DatabaseException(e.getMessage());
        }
    }

    private void createTransactionTable() throws DatabaseException {
        try (Statement statement = this.connection.createStatement()) {
            String sql = """
                        CREATE TABLE IF NOT EXISTS transaction (
                                 id SERIAL,
                                 sender_customer_id INT,
                                 receiver_customer_id INT,
                                 amount DECIMAL(10, 2),
                                 sender_account_id INT,
                                 receiver_account_id INT,
                                 sender_bank_id INT,
                                 receiver_bank_id INT,
                                 comment TEXT,
                                 CONSTRAINT transaction_pk PRIMARY KEY (id),
                                 CONSTRAINT fk_sender_customer FOREIGN KEY (sender_customer_id) REFERENCES customer (id),
                                 CONSTRAINT fk_receiver_customer FOREIGN KEY (receiver_customer_id) REFERENCES customer (id),
                                 CONSTRAINT fk_sender_account FOREIGN KEY (sender_account_id) REFERENCES account (id),
                                 CONSTRAINT fk_receiver_account FOREIGN KEY (receiver_account_id) REFERENCES account (id),
                                 CONSTRAINT fk_sender_bank FOREIGN KEY (sender_bank_id) REFERENCES bank (id),
                                 CONSTRAINT fk_receiver_bank FOREIGN KEY (receiver_bank_id) REFERENCES bank (id)
                             );
                    """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("Transaction table creation failed!");
            throw new DatabaseException(e.getMessage());
        }
    }
}
