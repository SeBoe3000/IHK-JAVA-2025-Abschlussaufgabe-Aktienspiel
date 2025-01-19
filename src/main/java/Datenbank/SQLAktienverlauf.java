package Datenbank;

import Backend.ElementAktienverlauf;
import Frontend.Komponenten.Interaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLAktienverlauf {
    // Tabelle Transaktion füllen - mit Rückgabe mind. ein Datensatz bereits in Datenbank vorhanden
    public static boolean selectInsertTableAktienverlauf(List<ElementAktienverlauf> aktienverlauf){
        Boolean insert = false;
        String sqlSelect = "SELECT count(*) FROM Aktienverlauf WHERE Runde = ? AND AktieISIN = ?";
        String sqlInsert = "INSERT INTO Aktienverlauf (Runde, AktieISIN, Aktienanzahl, Aktienkurs, Kassenbestand) VALUES (?,?,?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            for(ElementAktienverlauf Aktienverlauf: aktienverlauf){
                // SELECT
                pstmtSelect.setInt(1, Aktienverlauf.getRunde());
                pstmtSelect.setString(2, Aktienverlauf.getAktie());
                ResultSet resultSelect = pstmtSelect.executeQuery();
                int result = 1;
                while(resultSelect.next()){
                    result = Integer.parseInt(resultSelect.getString(1));
                    // System.out.println(result);
                }
                if(result == 0) {
                    // INSERT
                    pstmtInsert.setInt(1, Aktienverlauf.getRunde());
                    pstmtInsert.setString(2, Aktienverlauf.getAktie());
                    pstmtInsert.setInt(3, Aktienverlauf.getAnzahl());
                    pstmtInsert.setFloat(4, Aktienverlauf.getKurs());
                    pstmtInsert.setFloat(5, Aktienverlauf.getKassenbestand());
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
