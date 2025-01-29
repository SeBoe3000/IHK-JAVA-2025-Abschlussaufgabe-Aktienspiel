package Frontend.Checks;

import Backend.Fehler;
import Frontend.Komponenten.EingabePanel;
import Frontend.Komponenten.EingabePanelVonBis;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // Einstiegspunkt für diverse Prüfungen
    public static void checkField(EingabePanel input, String checkType, String errorMessage, ArrayList<String> errorMessages, List<Fehler> errorFlags){
        Boolean check = Checks.checkValues(input, checkType, 0,0, 0F, 0F);
        addError(check, input, errorMessage, errorMessages, errorFlags);
    }
    public static void checkFieldLenght(EingabePanel input, Integer von, Integer bis, String checkType, String errorMessage, ArrayList<String> errorMessages, List<Fehler> errorFlags){
        Boolean check = Checks.checkValues(input, checkType, von, bis,0F, 0F);
        addError(check, input, errorMessage, errorMessages, errorFlags);
    }
    public static void checkFieldLenghtFloat(EingabePanel input, Float von, Float bis, String checkType, String errorMessage, ArrayList<String> errorMessages, List<Fehler> errorFlags){
        Boolean check = Checks.checkValues(input, checkType, 0,0, von, bis);
        addError(check, input, errorMessage, errorMessages, errorFlags);
    }

    // Prüfung auf gültige Eingaben
    public static boolean checkValues(EingabePanel input, String checkArt, Integer vonInt, Integer bisInt, Float vonFloat, Float bisFloat) {
        String check = input.getTextfield();
        boolean check1 = false;

        if (checkArt == "isValidString") {
            check1 = EingabenCheck.isValidString(check);
        }  else if (checkArt == "isValidStringLaenge") {
            check1 = EingabenCheck.isValidStringLaenge(check, vonInt, bisInt);
        } else if (checkArt == "isValidInteger") {
            check1 = EingabenCheck.isValidInteger(check, "NOTNULL");
        } else if (checkArt == "isValidFloat") {
            check1 = EingabenCheck.isValidFloat(check, "NOTNULL");
        } else if (checkArt == "isValidFloatNull") {
            check1 = EingabenCheck.isValidFloat(check, "NULL");
        } else if (checkArt == "isValidFloatEmpty") {
            check1 = EingabenCheck.isValidFloat(check, "EMPTY");
        } else if (checkArt == "isValidIntegerVonBis") {
            check1 = EingabenCheck.isValidIntegerVonBis(check, vonInt, bisInt);
        } else if (checkArt == "isValidFloatVonBis") {
            check1 = EingabenCheck.isValidFloatVonBis(check, vonFloat, bisFloat);
        }
        return check1;
    }

    // Fehler hinzufügen und Flag setzen für Schriftfarbe ändern
    public static void addError (Boolean check, EingabePanel input, String errorMessage, ArrayList<String> errorMessages, List<Fehler> errorFlags){
        if (!check){
            errorMessages.add(errorMessage);
            errorFlags.add(new Fehler(true, input));
        } else {
            errorFlags.add(new Fehler(false, input));
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
            // Nach der Ausgabe die Liste leeren
            errorMessages.clear();
        }
    }

    // Setzt die Schriftfarbe vom Feld rot, wenn ein Fehler aufgetreten ist.
    public static void setFarbeFelder(List<Fehler> errorFlags) {
        // Ermittlung aller vorkommenden Panels (als Set und in Liste umwandeln)
        Set<EingabePanel> panelSet = new HashSet<>();
        for (Fehler fehler : errorFlags) {
            panelSet.add(fehler.getField());
        }
        List<EingabePanel> panelList = new ArrayList<>(panelSet);

        // Liste der einzigartigen Eingabe-Panels durchgehen
        for (int i = 0; i < panelSet.size(); i++) {
            EingabePanel panel = panelList.get(i);
            // Für jedes einzigartige Eingabe-Panel prüfen, ob ein Fehler vorhanden ist.
            for (Fehler fehler : errorFlags) {
                panel.removeError();  // Schwarz
                // liegt ein Fehler vor (getErrorFlag = true), wird das Feld auf rot gesetzt
                if (fehler.getField() == panel && fehler.getErrorFlag()) {
                    panel.setError();  // Rot
                    break;
                }
            }
        }
    }

    // Feld leeren und Fehler entfernen
    public static void clearOneField(EingabePanel field){
        field.setTextField("");
        field.removeError();
    }





    // Für das EingabepanelVonBis
    // Einstiegspunkt für diverse Prüfungen
    public static void checkFieldVonBis(EingabePanelVonBis input, String checkType, String errorMessage, ArrayList<String> errorMessages, List<Fehler> errorFlags){
        Boolean check = Checks.checkValuesVonBis(input, checkType, 0,0);
        addErrorVonBis(check, input, errorMessage, errorMessages, errorFlags);

        // Bei keinem Fehler in Abhängigkeit von der Prüfart den Input parsen und Fehler hinzufügen wenn von größer bis
        if(check){
            if(checkType == "isValidInteger" || checkType == "isValidIntegerNull") {
                Integer von = Integer.valueOf(input.getTextfieldVon());
                Integer bis = Integer.valueOf(input.getTextfieldBis());
                if(von > bis){
                    errorMessages.add("Von ("+ input.getTextfieldVon() + ") darf nicht größer als Bis (" + input.getTextfieldBis() + ") sein, im Feld: " + input.getTextlabel());
                    errorFlags.add(new Fehler(true, input));
                } else if (bis == 0) {
                    errorMessages.add("Bis darf nicht Null sein, Feld: " + input.getTextlabel());
                    errorFlags.add(new Fehler(true, input));
                }
            } else if (checkType == "isValidFloat" || checkType == "isValidFloatNull") {
                Float von = Float.valueOf(input.getTextfieldVon());
                Float bis = Float.valueOf(input.getTextfieldBis());
                if(von > bis){
                    errorMessages.add("Von ("+ input.getTextfieldVon() + ") darf nicht größer als Bis (" + input.getTextfieldBis() + ") sein, im Feld: " + input.getTextlabel());
                    errorFlags.add(new Fehler(true, input));
                } else if (bis == 0) {
                    errorMessages.add("Bis darf nicht Null sein, Feld: " + input.getTextlabel());
                    errorFlags.add(new Fehler(true, input));
                }
            }
        }
    }

    // Für das EingabepanelVonBis
    // Prüfung auf gültige Eingaben
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

        if (!check1Von || !check1Bis) {
            return false;
        } else {
            return true;
        }
    }

    // Für das EingabepanelVonBis
    // Fehler hinzufügen und Flag setzen für Schriftfarbe ändern
    public static void addErrorVonBis (Boolean check, EingabePanelVonBis input, String errorMessage, ArrayList<String> errorMessages, List<Fehler> errorFlags){
        if (!check){
            errorMessages.add(errorMessage);
            errorFlags.add(new Fehler(true, input));
        } else {
            errorFlags.add(new Fehler(false, input));
        }
    }

    // Für das EingabepanelVonBis
    // Feld leeren und Fehler entfernen
    public static void clearOneFieldVonBis(EingabePanelVonBis field){
        field.setTextFieldVon("");
        field.setTextFieldBis("");
        field.removeErrorVon();
        field.removeErrorBis();
    }

    // Für das EingabepanelVonBis
    // Setzt die Schriftfarbe vom Feld rot, wenn ein Fehler aufgetreten ist.
    public static void setFarbeFelderVonBis(List<Fehler> errorFlags) {
        // Ermittlung aller vorkommenden Panels (als Set und in Liste umwandeln)
        Set<EingabePanelVonBis> panelSet = new HashSet<>();
        for (Fehler fehler : errorFlags) {
            panelSet.add(fehler.getFieldVonBis());
        }
        List<EingabePanelVonBis> panelList = new ArrayList<>(panelSet);

        // Liste der einzigartigen Eingabe-Panels durchgehen
        for (int i = 0; i < panelSet.size(); i++) {
            EingabePanelVonBis panel = panelList.get(i);
            // Für jedes einzigartige Eingabe-Panel prüfen, ob ein Fehler vorhanden ist.
            for (Fehler fehler : errorFlags) {
                panel.removeErrorVon();  // Schwarz
                panel.removeErrorBis();  // Schwarz
                if (fehler.getFieldVonBis() == panel && fehler.getErrorFlag()) {
                    panel.setErrorVon();  // Rot
                    panel.setErrorBis();  // Rot
                    break;
                }
            }
        }
    }
}
