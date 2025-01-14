package Datenbank;

import Backend.ElementTransaktionen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLTransaktionen {
    // Tabelle Transaktion füllen - mit Rückgabe mind. ein Datensatz bereits in Datenbank vorhanden
    public static boolean selectInsertTableTransaktionen(List<ElementTransaktionen> transaktionen){
        Boolean insert = false;
        String sqlSelect = "SELECT count(*) FROM Transaktionen WHERE Runde = ? AND PersonID = ? AND AktieISIN = ?";
        String sqlInsert = "INSERT INTO Transaktionen (Runde, PersonID, AktieISIN, Aktienanzahl, Dividende) VALUES(?,?,?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            for(ElementTransaktionen Transaktionen: transaktionen){
                // SELECT
                pstmtSelect.setInt(1, Transaktionen.getRunde());
                pstmtSelect.setInt(2, Transaktionen.getPerson());
                pstmtSelect.setString(3, Transaktionen.getAktie());
                ResultSet resultSelect = pstmtSelect.executeQuery();
                int result = 1;
                while(resultSelect.next()){
                    result = Integer.parseInt(resultSelect.getString(1));
                    // System.out.println(result);
                }
                if(result == 0) {
                    // INSERT
                    pstmtInsert.setInt(1, Transaktionen.getRunde());
                    pstmtInsert.setInt(2, Transaktionen.getPerson());
                    pstmtInsert.setString(3, Transaktionen.getAktie());
                    pstmtInsert.setInt(4, Transaktionen.getAnzahl());
                    pstmtInsert.setFloat(5, Transaktionen.getDividende());
                    pstmtInsert.addBatch();
                    if (++count % batchSize == 0) {
                        pstmtInsert.executeBatch();
                    }
                    insert = true;
                }
            }
            pstmtInsert.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return insert;
    }
}
