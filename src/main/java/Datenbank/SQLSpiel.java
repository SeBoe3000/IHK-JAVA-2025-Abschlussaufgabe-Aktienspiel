package Datenbank;

import Frontend.Komponenten.Interaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLSpiel {
    // Ermittlung Runden
    public static Integer getRunde(String tabelle){
        Integer ergebnis = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement prepStmt = conn.prepareStatement("SELECT Runde FROM ? ORDER BY Runde DESC LIMIT 1")
        ){
            prepStmt.setString(1, tabelle);
            ResultSet rs = prepStmt.executeQuery();
            while(rs.next()){
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

    // Ermittlung Anzahl Spieler und Aktien in der aktuellen Runde
    public static Integer getSpieldetails(String field){
        Integer ergebnis = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement prepStmt = conn.prepareStatement("SELECT COUNT(DISTINCT ?) FROM Transaktionen WHERE Runde = (SELECT MAX(Runde) FROM transaktionen);")


        ){
            prepStmt.setString(1, field);
            ResultSet rs = prepStmt.executeQuery();
            while(rs.next()){
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


    /*
    SQL's:
    Wie häufig kommen Aktien in der aktuellen Runde vor?
    SELECT COUNT(*), Aktieisin, Runde FROM Transaktionen WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen) GROUP BY Runde, Aktieisin;

    Wie häufig kommen Personen in der aktuellen Runde vor?
    SELECT COUNT(*), Personid, Runde FROM Transaktionen WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen) GROUP BY Runde, Personid;

    Gibt es in der aktuellen Runde zu einer Aktie keinen Kassenbestand?
    SELECT COUNT(CASE WHEN aktienverlauf.kassenbestand IS NULL THEN 1 END)
    FROM Transaktionen LEFT JOIN Aktienverlauf ON
    Transaktionen.Runde = Aktienverlauf.Runde AND Transaktionen.Aktieisin = Aktienverlauf.Aktieisin
    WHERE Transaktionen.Runde = (SELECT MAX(Transaktionen.Runde) FROM Transaktionen);

    Zu welchen Aktien gibt es keinen Kassenbestand?
    SELECT Transaktionen.Aktieisin
    FROM Transaktionen LEFT JOIN Aktienverlauf
    ON Transaktionen.Runde = Aktienverlauf.runde AND Transaktionen.Aktieisin = Aktienverlauf.Aktieisin
    WHERE Transaktionen.Runde = (SELECT MAX(Transaktionen.Runde) FROM Transaktionen)
    GROUP BY Transaktionen.Runde, Transaktionen.Aktieisin, Aktienverlauf.Kassenbestand
    HAVING COUNT(CASE WHEN Aktienverlauf.Kassenbestand IS NULL THEN 1 END) > 0
    ORDER BY Transaktionen.Runde, Transaktionen.Aktieisin;

     */


}
