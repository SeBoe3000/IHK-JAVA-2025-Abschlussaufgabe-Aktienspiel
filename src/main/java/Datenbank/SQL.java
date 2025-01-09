package Datenbank;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {

    // Verwendet um SQL's ohne Rückgabe und Check auszuführen
    public static void table(String inputquery){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = inputquery;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
