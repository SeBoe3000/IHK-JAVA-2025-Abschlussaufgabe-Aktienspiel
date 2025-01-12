package Frontend.ActionListener;

import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
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

    // Validiert Eingabe String in Kombination mit Methode checkValues. Bei gültiger Angabe wird Eingabe übernommen.
    // Hinweis: nur verwenden, wenn Feld nicht Null ist.
    public static String validateInputString(EingabePanel field, String fieldType, String errorMessage) {
        if (Checks.checkValues(field, fieldType, errorMessage)) {
            return field.getTextfield();
        }
        return "";
    }

    // Prüfung auf gültige Eingaben
    public static boolean checkValues(EingabePanel input, String checkArt, String fehlernachricht) {
        String check = input.getTextfield();
        boolean check1 = false;

        if (checkArt == "isValidString") {
            check1 = EingabenCheck.isValidString(check);
        }  else if (checkArt == "kennzeichen") {
            check1 = EingabenCheck.isValidKennzeichen(check);
        }

        // Text für Fehlermeldung
        String text = fehlernachricht;
        String title = "Fehler";

        // Bei korrekter Eingabe (z.B. nach Fehler) Schriftfarbe zurückändern.
        input.removeError();

        if (!(check1)) {
            // Beim Fehler die Schriftfarbe auf rotändern.
            input.setError();
            JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }
}
