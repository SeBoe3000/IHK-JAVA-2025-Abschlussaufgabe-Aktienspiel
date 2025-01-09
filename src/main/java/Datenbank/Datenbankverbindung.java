package Datenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datenbankverbindung {
    private static final String DB_URL = "jdbc:postgresql://localhost:1731/Abschlussaufgabe";
    private static final String USER = "postgres";
    private static final String PASS = "verysecure";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}