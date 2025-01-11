package Datenbank;

import Backend.ElementAktie;

import java.sql.*;
import java.util.List;

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

    // Insert insertTableAktie
    public static boolean insertTableAktie(List<ElementAktie> aktie){
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
                    //System.out.println(result);
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

}
