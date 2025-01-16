package Datenbank;

import Frontend.Komponenten.Interaction;

import java.sql.*;

public class SQLEinstellungen {

    // Rückgabe der Einstellung als String
    public static String getEinstellung(String typ){
        String ergebnis = "";
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement prepStmt = conn.prepareStatement("SELECT Einstellung FROM Einstellungen WHERE Typ = ?")
        ){
            prepStmt.setString(1, typ);
            ResultSet rs = prepStmt.executeQuery();
            while(rs.next()){
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

    // Update Einstellung
    public static Boolean setEinstellung(String typ,String einstellung){
        Boolean ergebnis = false;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement prepStmt = conn.prepareStatement("UPDATE Einstellungen SET Einstellung = ? WHERE typ = ?")
        ){
            prepStmt.setString(1, einstellung);
            prepStmt.setString(2, typ);
            int rs = prepStmt.executeUpdate();
            if(rs == 1){
                ergebnis = true;
            }
        } catch (SQLException e){
            Interaction.noDatabase();
            // e.printStackTrace();
        } catch (Exception e){
            Interaction.noDatabase();
            // e.printStackTrace();
        }
        return true;
    }
}
