package Datenbank;

import Backend.ElementAktie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLAktien {

    // Tabelle Aktie füllen - mit Rückgabe mind. ein Datensatz bereits in Datenbank vorhanden
    public static boolean selectInsertTableAktie(List<ElementAktie> aktie){
        Boolean insert = false;
        String sqlSelect = "SELECT count(*) FROM Aktien WHERE Isin = ?";
        String sqlInsert = "INSERT INTO Aktien VALUES(?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            for(ElementAktie Aktie: aktie){
                // SELECT
                pstmtSelect.setString(1, Aktie.getIsin());
                ResultSet resultSelect = pstmtSelect.executeQuery();
                int result = 1;
                while(resultSelect.next()){
                    result = Integer.parseInt(resultSelect.getString(1));
                    // System.out.println(result);
                }
                if(result == 0) {
                    // INSERT
                    pstmtInsert.setString(1, Aktie.getIsin());
                    pstmtInsert.setString(2, Aktie.getName());
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

    // Tabelle Aktie füllen
    // Für Option 2 im AktienListener.elementInsert benötigt.
    public static void insertTableAktie(List<ElementAktie> aktie){
        Boolean insert = false;
        String sqlInsert = "INSERT INTO Aktien VALUES(?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            for(ElementAktie Aktie: aktie){
                // INSERT
                pstmtInsert.setString(1, Aktie.getIsin());
                pstmtInsert.setString(2, Aktie.getName());
                pstmtInsert.addBatch();
                if (++count % batchSize == 0) {
                    pstmtInsert.executeBatch();
                }
                insert = true;
            }
            pstmtInsert.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
