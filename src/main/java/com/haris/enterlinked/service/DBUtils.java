package com.haris.enterlinked.service;
import java.io.IOException;
import java.sql.*;

public class DBUtils {

    private static String url =
            "jdbc:mysql://enterlinked-enterlinked.h.aivencloud.com:17844/EnterLinked" +
                    "?sslMode=REQUIRED" +
                    "&connectTimeout=10000" +
                    "&socketTimeout=10000";
    private static String DB_username = "avnadmin";
    private static  String DB_password = "AVNS_BnERW4syT9zZAohvYRU";

    public static Connection getConnection() throws SQLException{

        return DriverManager.getConnection(url,DB_username,DB_password);
    }


}
