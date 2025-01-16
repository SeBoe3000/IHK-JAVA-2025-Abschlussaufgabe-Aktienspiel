package Frontend.Checks;

import Frontend.Komponenten.EingabePanel;
import Frontend.Komponenten.EingabePanelVonBis;

import javax.swing.*;
import java.util.ArrayList;

public class Checks {

    // Prüfung, ob ein Wert in der Liste vorhanden ist.
    public static boolean checkElementInList(ArrayList name) {
        boolean noElement = false;
        if (name.isEmpty()) {
            noElement = true;
        }
        // System.out.println("noElement: " + noElement);
        return noElement;
    }

    // Prüfung, ob das Feld gefüllt ist
    public static boolean checkOneFieldfilled(EingabePanel field) {
        Boolean filled = true;
        if (field.getTextfield().isEmpty()) {
            filled = false;
        }
        // System.out.println("Eingabe:" + field.getTextfield() + "notInWork: " +  notInWork);
        return filled;
    }

    // Prüfung auf gültige Eingaben verkürzt darstellen
    public static void checkField(EingabePanel input, String checkType, String errorMessage, ArrayList<String> errorMessages){
        if (!Checks.checkValues(input, checkType, 0,0)) {
            errorMessages.add(errorMessage);
        }
    }

    public static void checkFieldLenght(EingabePanel input, Integer von, Integer bis, String checkType, String errorMessage, ArrayList<String> errorMessages){
        if (!Checks.checkValues(input, checkType, von, bis)) {
            errorMessages.add(errorMessage);
        }
    }

    // Prüfung auf gültige Eingaben
    public static boolean checkValues(EingabePanel input, String checkArt, Integer von, Integer bis) {
        String check = input.getTextfield();
        boolean check1 = false;

        if (checkArt == "isValidString") {
            check1 = EingabenCheck.isValidString(check);
        }  else if (checkArt == "isValidStringLaenge") {
            check1 = EingabenCheck.isValidStringLaenge(check, von, bis);
        } else if (checkArt == "isValidInteger") {
            check1 = EingabenCheck.isValidInteger(check, "NOTNULL");
        } else if (checkArt == "isValidFloat") {
            check1 = EingabenCheck.isValidFloat(check, "NOTNULL");
        } else if (checkArt == "isValidFloatNull") {
            check1 = EingabenCheck.isValidFloat(check, "NULL");
        } else if (checkArt == "isValidIntegerVonBis") {
            check1 = EingabenCheck.isValidIntegerVonBis(check, von, bis);
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

    // Für das EingabepanelVonBis
    public static void checkFieldVonBis(EingabePanelVonBis input, String checkType, String errorMessage, ArrayList<String> errorMessages){
        if (!Checks.checkValuesVonBis(input, checkType, 0,0)) {
            errorMessages.add(errorMessage);
        } else{
            // Bei keinem Fehler in Abhängigkeit von der Prüfart den Input parsen und Fehler hinzufügen wenn von größer bis
            if(checkType == "isValidInteger") {
                Integer von = Integer.valueOf(input.getTextfieldVon());
                Integer bis = Integer.valueOf(input.getTextfieldBis());
                if(von > bis){
                    errorMessages.add("Von ("+ input.getTextfieldVon() + ") darf nicht größer als Bis (" + input.getTextfieldBis() + ") sein, im Feld: " + input.getTextlabel());
                }
            } else if (checkType == "isValidFloat") {
                Float von = Float.valueOf(input.getTextfieldVon());
                Float bis = Float.valueOf(input.getTextfieldBis());
                if(von > bis){
                    errorMessages.add("Von ("+ input.getTextfieldVon() + ") darf nicht größer als Bis (" + input.getTextfieldBis() + ") sein, im Feld: " + input.getTextlabel());
                }
            }
        }
    }
    public static boolean checkValuesVonBis(EingabePanelVonBis input, String checkArt, Integer von, Integer bis) {
        String checkVon = input.getTextfieldVon();
        String checkBis = input.getTextfieldBis();
        boolean check1Von = false;
        boolean check1Bis = false;

        if (checkArt == "isValidInteger") {
            check1Von = EingabenCheck.isValidInteger(checkVon, "NOTNULL");
            check1Bis = EingabenCheck.isValidInteger(checkBis, "NOTNULL");
        } else if (checkArt == "isValidIntegerNull") {
            check1Von = EingabenCheck.isValidInteger(checkVon, "NULL");
            check1Bis = EingabenCheck.isValidInteger(checkBis, "NULL");
        } else if (checkArt == "isValidFloat") {
            check1Von = EingabenCheck.isValidFloat(checkVon,"NOTNULL");
            check1Bis = EingabenCheck.isValidFloat(checkBis,"NOTNULL");
        }else if (checkArt == "isValidFloatNull") {
            check1Von = EingabenCheck.isValidFloat(checkVon,"NULL");
            check1Bis = EingabenCheck.isValidFloat(checkBis,"NULL");
        }

        // Bei korrekter Eingabe (z.B. nach Fehler) Schriftfarbe zurückändern.
        input.removeErrorVon();
        input.removeErrorBis();

        if (!(check1Von)) {
            // Beim Fehler die Schriftfarbe auf rotändern.
            input.setErrorVon();
            return false;
        } else if (!check1Bis) {
            // Beim Fehler die Schriftfarbe auf rotändern.
            input.setErrorBis();
            return false;
        } else {
            return true;
        }
    }

    // Feld leeren und Fehler entfernen
    public static void clearOneField(EingabePanel field){
        field.setTextField("");
        field.removeError();
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
            // Nach der Ausgabe die Liste leeren
            errorMessages.clear();
        }
    }
}
