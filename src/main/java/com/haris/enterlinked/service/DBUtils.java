package com.haris.enterlinked.service;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.*;

public class DBUtils {

    private static String DB_username = "avnadmin";
    private static  String DB_password = "AVNS_BnERW4syT9zZAohvYRU";
   // private static String Url = "jdbc:mysql://enterlinked-enterlinked.h.aivencloud.com:17844/EnterLinked";


    private static HikariDataSource ds;
    private static HikariConfig config = new HikariConfig();
    static {
        config.setJdbcUrl(  "jdbc:mysql://enterlinked-enterlinked.h.aivencloud.com:17844/EnterLinked" + "?sslMode=REQUIRED&connectTimeout=3000&socketTimeout=10000&tcpKeepAlive=true");
        config.setUsername(DB_username);
        config.setPassword(DB_password);
        config.setMaximumPoolSize(4);
        config.setMinimumIdle(0);
        config.setInitializationFailTimeout(-1);
        config.setConnectionTimeout(20000);
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
       // return DriverManager.getConnection(Url,DB_username,DB_password);
       return ds.getConnection();
    }
}
