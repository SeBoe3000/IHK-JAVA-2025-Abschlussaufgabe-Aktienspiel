package Datenbank;

import java.sql.*;

public class SQL {

    // SQL's ohne Rückgabe und Check ausführen (z.B. Tabellen erstellen)
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

    // Prüfung, ob Wert bereits in Datenbank vorhanden (Übergabe von einem String)
    public static boolean checkElementAlreadyInDatenbankOneString(String eingabe, String field, String table){
        boolean inDatenbank = false;

        String sqlSelect = "SELECT count(*) FROM " + table + " WHERE " + field + " = ?";
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
        ){
            pstmtSelect.setString(1, eingabe);
            ResultSet resultSelect = pstmtSelect.executeQuery();
            int result = 1;
            while (resultSelect.next()) {
                result = Integer.parseInt(resultSelect.getString(1));
                // System.out.println(result);
                if (result == 1) {
                    inDatenbank = true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return inDatenbank;
    }

    // Prüfung, ob Wert bereits in Datenbank vorhanden (Übergabe von einem Integer)
    public static boolean checkElementAlreadyInDatenbankOneInteger(Integer eingabe, String field, String table){
        boolean inDatenbank = false;
        String sqlSelect = "SELECT count(*) FROM " + table + " WHERE " + field + " = ?";
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
        ){
            pstmtSelect.setInt(1, eingabe);
            ResultSet resultSelect = pstmtSelect.executeQuery();
            int result = 1;
            while (resultSelect.next()) {
                result = Integer.parseInt(resultSelect.getString(1));
                // System.out.println(result);
                if (result == 1) {
                    inDatenbank = true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return inDatenbank;
    }

    // Prüfung, ob Wert bereits in Datenbank vorhanden (Übergabe von der Kombination String, String, Integer)
    public static boolean checkElementAlreadyInDatenbankStringStringInteger(String eingabe1, String eingabe2, Integer eingabe3, String field1, String field2, String field3, String table){
        boolean inDatenbank = false;

        String sqlSelect = "SELECT count(*) FROM " + table + " WHERE "
                + field1 + " = ? AND "
                + field2 + " = ? AND "
                + field3 + " = ?";
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
        ){
            pstmtSelect.setString(1, eingabe1);
            pstmtSelect.setString(2, eingabe2);
            pstmtSelect.setInt(3, eingabe3);
            ResultSet resultSelect = pstmtSelect.executeQuery();
            int result = 1;
            while (resultSelect.next()) {
                result = Integer.parseInt(resultSelect.getString(1));
                // System.out.println(result);
                if (result == 1) {
                    inDatenbank = true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return inDatenbank;
    }






}
