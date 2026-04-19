package com.haris.enterlinked.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

    private static String DB_username = "avnadmin";
    private static String DB_password = "AVNS_BnERW4syT9zZAohvYRU";

    private static HikariDataSource ds;
    private static HikariConfig config = new HikariConfig();

    static {
        config.setJdbcUrl("jdbc:mysql://enterlinked-enterlinked.h.aivencloud.com:17844/EnterLinked"
                + "?sslMode=DISABLED&connectTimeout=10000&socketTimeout=20000");
        config.setUsername(DB_username);
        config.setPassword(DB_password);
        config.setMaximumPoolSize(4);
        config.setMinimumIdle(0);
        config.setInitializationFailTimeout(-1);
        config.setConnectionTimeout(30000);
        config.setValidationTimeout(3000);
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Attempting DB connection...");
        Connection connection = ds.getConnection();
        System.out.println("DB connection successful");
        return connection;
    }
}