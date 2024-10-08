package com.malivasileva.data;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://10.0.2.2:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1111";

    private static final String[] DEFAULT = {"auth", "1111"};
    private static final String[] READER = {"reader", "1111"};
    private static final String[] LIBRARIAN = {"libr", "1111"};

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static Connection getAuthConnection() {
        Properties props = new Properties();
        props.setProperty("user", "auth");
        props.setProperty("password", "1111");
        try {
            return DriverManager.getConnection(URL, props);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getReaderConnection() throws SQLException {

        try {
            return DriverManager.getConnection(URL, READER[0], READER[1]);
//            return readerDataSource.getConnection();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getLibrConnection() throws SQLException {

        try {
            return DriverManager.getConnection(URL, LIBRARIAN[0], LIBRARIAN[1]);
//            return librarianDataSource.getConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
