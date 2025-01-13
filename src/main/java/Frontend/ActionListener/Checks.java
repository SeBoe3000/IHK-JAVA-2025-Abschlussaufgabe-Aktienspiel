package Frontend.ActionListener;

import Frontend.Komponenten.EingabePanel;

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

    // Prüfung auf gültige Eingaben verkürzt darstellen
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
            check1 = EingabenCheck.isValidInteger(check);
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

    // Feld leeren und Fehler entfernen
    public static void clearOneField(EingabePanel field){
        field.setTextField("");
        field.removeError();
    }
}
