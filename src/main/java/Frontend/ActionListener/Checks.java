package Frontend.ActionListener;

import Datenbank.Datenbankverbindung;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Checks {

    // Prüfung, ob ein Wert in der Liste vorhanden ist.
    public static boolean checkElementInList(ArrayList name) {
        boolean noElement = false;
        if (name.isEmpty()) {
            noElement = true;
        }
        //System.out.println("noElement: " + noElement);
        return noElement;
    }

    // Prüfung, ob das Feld gefüllt ist
    public static boolean checkOneFieldfilled(EingabePanel field) {
        Boolean notInWork = false;
        if (field.getTextfield().isEmpty()) {
            notInWork = true;
        }
        // System.out.println("Eingabe:" + field.getTextfield() + "notInWork: " +  notInWork);
        return notInWork;
    }

    // Prüfung, ob der Wert bereits in der Datenbank vorhanden ist (Übergabe von einem String)
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

    // Prüfung auf gültige Eingaben verkürzt darstellen
    public static void checkField(EingabePanel input, String checkType, String errorMessage, ArrayList<String> errorMessages){
        if (!Checks.checkValues(input, checkType)) {
            errorMessages.add(errorMessage);
        }
    }

    // Prüfung auf gültige Eingaben
    public static boolean checkValues(EingabePanel input, String checkArt) {
        String check = input.getTextfield();
        boolean check1 = false;

        if (checkArt == "isValidString") {
            check1 = EingabenCheck.isValidString(check);
        }  else if (checkArt == "kennzeichen") {
            check1 = EingabenCheck.isValidString(check);
        }

        // Bei korrekter Eingabe (z.B. nach Fehler) Schriftfarbe zurückändern.
        input.removeError();

        if (!(check1)) {
            // Beim Fehler die Schriftfarbe auf rotändern.
            input.setError();
            return false;
        } else {
            return true;
        }
    }

    // Fehlermeldungen ausgeben
    public static void showError (ArrayList<String> errorMessages){
        if (!errorMessages.isEmpty()) {
            StringBuilder message = new StringBuilder();
            if(errorMessages.size() > 1) {
                message.append("Folgende Fehler sind aufgetreten:\n");
            }
            for (String errorMessage : errorMessages) {
                message.append(errorMessage).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString(), "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Element wurde der Liste hinzugefügt.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Feld leeren und Fehler entfernen
    public static void clearOneField(EingabePanel field){
        field.setTextField("");
        field.removeError();
    }
}
