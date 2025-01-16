package Datenbank;

import Backend.ElementKapitalverlauf;
import Frontend.Komponenten.Interaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLKapitalverlauf {
    // Tabelle Kapitalverlauf füllen - mit Rückgabe mind. ein Datensatz bereits in Datenbank vorhanden
    public static boolean selectInsertTableKapitalverlauf(List<ElementKapitalverlauf> kapitalverlauf){
        Boolean insert = false;
        String sqlSelect = "SELECT count(*) FROM Kapitalverlauf WHERE Runde = ? AND PersonID = ? AND Kapital = ?";
        String sqlInsert = "INSERT INTO Kapitalverlauf (Runde, Personid, Kapital) VALUES(?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            for(ElementKapitalverlauf Kapitalverlauf: kapitalverlauf){
                // SELECT
                pstmtSelect.setInt(1, Kapitalverlauf.getRunde());
                pstmtSelect.setInt(2, Kapitalverlauf.getPerson());
                pstmtSelect.setFloat(3, Kapitalverlauf.getBetrag());
                ResultSet resultSelect = pstmtSelect.executeQuery();
                int result = 1;
                while(resultSelect.next()){
                    result = Integer.parseInt(resultSelect.getString(1));
                    // System.out.println(result);
                }
                if(result == 0) {
                    // INSERT
                    pstmtInsert.setInt(1, Kapitalverlauf.getRunde());
                    pstmtInsert.setInt(2, Kapitalverlauf.getPerson());
                    pstmtInsert.setFloat(3, Kapitalverlauf.getBetrag());
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
