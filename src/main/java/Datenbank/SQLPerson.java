package Datenbank;

import Backend.ElementPerson;
import Frontend.Komponenten.Interaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLPerson {
    // Tabelle Person füllen - mit Rückgabe mind. ein Datensatz bereits in Datenbank vorhanden
    public static boolean selectInsertTablePerson(List<ElementPerson> person){
        Boolean insert = false;
        String sqlSelect = "SELECT count(*) FROM Personen WHERE Vorname = ? AND Nachname = ? AND Alter = ?";
        String sqlInsert = "INSERT INTO Personen (Vorname, Nachname, Alter) VALUES(?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            for(ElementPerson Person: person){
                // SELECT
                pstmtSelect.setString(1, Person.getVorname());
                pstmtSelect.setString(2, Person.getNachname());
                pstmtSelect.setInt(3, Person.getAlter());
                ResultSet resultSelect = pstmtSelect.executeQuery();
                int result = 1;
                while(resultSelect.next()){
                    result = Integer.parseInt(resultSelect.getString(1));
                    // System.out.println(result);
                }
                if(result == 0) {
                    // INSERT
                    pstmtInsert.setString(1, Person.getVorname());
                    pstmtInsert.setString(2, Person.getNachname());
                    pstmtInsert.setInt(3, Person.getAlter());
                    pstmtInsert.addBatch();
                    if (++count % batchSize == 0) {
                        pstmtInsert.executeBatch();
                    }
                    insert = true;
                }
            }
            pstmtInsert.executeBatch();
        } catch (SQLException e){
            Interaction.noDatabase();
            // e.printStackTrace();
        } catch (Exception e){
            Interaction.noDatabase();
            // e.printStackTrace();
        }
        return insert;
    }
}