package Datenbank;

import Frontend.Komponenten.Interaction;

import java.sql.*;
import java.util.ArrayList;

public class SQLSpiel {
    // Ermittlung eines Integers
    public static Integer getOneInteger(String sql){
        Integer ergebnis = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement prepStmt = conn.prepareStatement(sql)
        ){
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()){
                ergebnis = rs.getInt(1);
            }
        } catch (SQLException e){
            Interaction.noDatabase();
            // e.printStackTrace();
        } catch (Exception e){
            Interaction.noDatabase();
            // e.printStackTrace();
        }
        return ergebnis;
    }

    // Ermittlung eines Integers
    public static Float getOneFloat(String sql){
        Float ergebnis = 0F;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement prepStmt = conn.prepareStatement(sql)
        ){
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()){
                ergebnis = rs.getFloat(1);
            }
        } catch (SQLException e){
            Interaction.noDatabase();
            // e.printStackTrace();
        } catch (Exception e){
            Interaction.noDatabase();
            // e.printStackTrace();
        }
        return ergebnis;
    }

    // Ermittlung AktienOhneWert
    public static String getAktienOhneWert(){
        String ergebnis = "";
        String query = "SELECT Transaktionen.Aktieisin " +
                "FROM Transaktionen LEFT JOIN Aktienverlauf " +
                "ON Transaktionen.Runde = Aktienverlauf.runde AND Transaktionen.Aktieisin = Aktienverlauf.Aktieisin " +
                "WHERE Transaktionen.Runde = (SELECT MAX(Transaktionen.Runde) FROM Transaktionen) " +
                "GROUP BY Transaktionen.Runde, Transaktionen.Aktieisin, Aktienverlauf.Kassenbestand " +
                "HAVING COUNT(CASE WHEN Aktienverlauf.Kassenbestand IS NULL THEN 1 END) > 0 " +
                "ORDER BY Transaktionen.Runde, Transaktionen.Aktieisin";
        try(
                Connection conn = Datenbankverbindung.connect();
                Statement stmt = conn.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(query);
            boolean one = true;
            while (rs.next()) {
                if (!one) {
                    ergebnis = ergebnis + ", "; // Komma vor den weiteren Werten anhängen
                }
                ergebnis = ergebnis + rs.getString(1); // Werte anhängen
                one = false;
            }
        } catch (SQLException e){
            Interaction.noDatabase();
             e.printStackTrace();
        } catch (Exception e){
            Interaction.noDatabase();
             e.printStackTrace();
        }
        return ergebnis;
    }

    //Rückgabe einer Arrayliste String
    public static ArrayList<String> getArrayListeString(String sql) {
        ArrayList<String> result = new ArrayList<>();

        try (Connection conn = Datenbankverbindung.connect();
             PreparedStatement prepStmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Rückgabe einer Arrayliste Integer
    public static ArrayList<Integer> getArrayListeInteger(String sql) {
        ArrayList<Integer> result = new ArrayList<>();

        try (Connection conn = Datenbankverbindung.connect();
             PreparedStatement prepStmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
