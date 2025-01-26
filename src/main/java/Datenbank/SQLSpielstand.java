package Datenbank;

import Backend.ElementSpielstand;
import Frontend.Komponenten.Interaction;

import java.sql.*;

public class SQLSpielstand {
    // Tabelle Spielstand füllen
    public static void insertTableSpielstand(ElementSpielstand spielstand){
        String sqlInsert = "INSERT INTO Spielstand VALUES(?,?,?,?,?)";
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            // INSERT
            pstmtInsert.setInt(1, spielstand.getRunde());
            pstmtInsert.setString(2, spielstand.getGewinnerSpieler());
            pstmtInsert.setString(3, spielstand.getMaxAktienSpieler());
            pstmtInsert.setString(4, spielstand.getMaxAktienUnternehmen());
            pstmtInsert.setString(5, spielstand.getGewinnUnternehmen());
            pstmtInsert.executeUpdate();
        } catch (SQLException e){
            Interaction.noDatabase();
            // e.printStackTrace();
        } catch (Exception e){
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    public static String abfrageGewinnerSpieler(){
        String ergebnis = "";
        String query = "SELECT string_agg(CONCAT(Personen.Vorname, ' ', Personen.Nachname), ' und '), Kapitalverlauf.Kapital " +
                "FROM Kapitalverlauf JOIN Personen ON Personen.ID = Kapitalverlauf.PersonID " +
                "WHERE Kapitalverlauf.Runde = (SELECT MAX(Kapitalverlauf.Runde) FROM Kapitalverlauf) " +
                "GROUP BY Kapitalverlauf.Kapital " +
                "ORDER BY Kapitalverlauf.Kapital DESC " +
                "LIMIT 1";
        try(
                Connection conn = Datenbankverbindung.connect();
                Statement stmt = conn.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String Personen = rs.getString(1);
                String Kapital = String.valueOf(rs.getInt(2));
                ergebnis = Personen + " haben/hat " + Kapital + " Euro.";
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

    public static String abfrageMaxAktienSpieler(){
        String ergebnis = "";
        String query = "SELECT string_agg(DISTINCT Summe.Person, ' und ') AS Personen, Summe.SummAktien " +
                "FROM (" +
                "SELECT Personen.Vorname || ' ' || Personen.Nachname AS Person, " +
                "SUM(Transaktionen.Aktienanzahl) AS SummAktien " +
                "FROM Transaktionen " +
                "JOIN Personen ON Personen.ID = Transaktionen.PersonID " +
                "WHERE Transaktionen.Runde = (SELECT MAX(Runde) FROM Transaktionen) " +
                "GROUP BY Personen.ID " +
                ") AS Summe " +
                "GROUP BY Summe.SummAktien " +
                "ORDER BY Summe.SummAktien DESC " +
                "LIMIT 1";
        try(
                Connection conn = Datenbankverbindung.connect();
                Statement stmt = conn.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String Personen = rs.getString(1);
                String Aktienanzahl = String.valueOf(rs.getInt(2));
                ergebnis = Personen + " haben/hat " + Aktienanzahl + " Aktien.";
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


    public static String abfrageMaxAktienUnternehmen(){
        String ergebnis = "";
        String query = "SELECT string_agg(DISTINCT Summe.Name, ' und ') AS Unternehmen, Summe.SummAktien " +
                "FROM (" +
                "SELECT Name, " +
                "SUM(Transaktionen.Aktienanzahl) AS SummAktien " +
                "FROM Transaktionen " +
                "JOIN Aktien ON Aktien.ISIN = Transaktionen.AktieISIN " +
                "WHERE Transaktionen.Runde = (SELECT MAX(Runde) FROM Transaktionen) " +
                "GROUP BY Aktien.ISIN " +
                ") AS Summe " +
                "GROUP BY Summe.SummAktien " +
                "ORDER BY Summe.SummAktien DESC " +
                "LIMIT 1";
        try(
                Connection conn = Datenbankverbindung.connect();
                Statement stmt = conn.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String Unternehmen = rs.getString(1);
                String Aktien = String.valueOf(rs.getInt(2));
                ergebnis = Unternehmen + " haben/hat " + Aktien + " Aktien verkauft.";
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


    public static String abfrageGewinnUnternehmen(){
        String ergebnis = "";
        String query = "SELECT string_agg(Aktien.name, ' und ') , Aktienverlauf.Kassenbestand " +
                "FROM Aktienverlauf join Aktien on Aktien.ISIN = Aktienverlauf.Aktieisin " +
                "WHERE Aktienverlauf.Runde = (SELECT MAX(Aktienverlauf.Runde) FROM Aktienverlauf) " +
                "GROUP BY Aktienverlauf.Kassenbestand " +
                "ORDER BY Aktienverlauf.Kassenbestand DESC " +
                "LIMIT(1)";
        try(
                Connection conn = Datenbankverbindung.connect();
                Statement stmt = conn.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String Unternehmen = rs.getString(1);
                String Gewinn = String.valueOf(rs.getInt(2));
                ergebnis = Unternehmen + " haben/hat " + Gewinn + " Kassenbestand.";
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

    // Ermittlung eines Strings
    public static String getOneString(String sql){
        String ergebnis = "";
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement prepStmt = conn.prepareStatement(sql)
        ){
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()){
                ergebnis = rs.getString(1);
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
}
