package models;

import java.sql.*;

public class Database {
    private static final String URL =
            "jdbc:mysql://itstud.hiof.no:3306/se25_G9?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "gruppe9";
    private static final String PASSWORD = "Summer28";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
