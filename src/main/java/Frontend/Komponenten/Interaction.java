package Frontend.Komponenten;

import javax.swing.*;

public class Interaction {

    // Frägt beim Abbrechen, ob die Änderungen gespeichert werden sollen und gibt die Antwort zurück
    public static boolean abbrechen(){
        Boolean action = true;
        String[] options = {"Ja", "Nein"};
        String message = "Sollen die Änderungen gespeichert werden?";
        String icon = "Speichern?";
        int jaNein = JOptionPane.showOptionDialog(null, message, icon, JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Bei Nein und Schließen False zurückgeben. Bei Ja wird true durch die Initialisierung weitergegeben
        if(jaNein == 1 || jaNein == -1){
            action = false;
        }
        return action;
    }

    // Fehlermeldung, wenn keine Datenbankverbindung vorhanden
    public static void noDatabase(){
        String title = "Allgemeiner Fehler";
        String text = "Es ist ein allgemeiner Fehler bei der Verarbeitung der Daten aufgetreten.\n" +
                "Bitte die Datenbankverbindung prüfen.\n" +
                "Wenden Sie sich an einen IT-Administrator.";
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }

    // Hinweismeldung, wenn Einstellung geändert wurde
    public static void einstellungChanged(){
        String title = "Erfolg";
        String text = "Die Einstellung wurde erfolgreich geändert.";
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Fehlermeldung, wenn keine Checkbox markiert
    public static void nothingMarked(){
        String title = "Keine Auswahl getroffen";
        String text = "Bitte markieren Sie eine Checkbox";
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }

    // Fehlermeldung, wenn Feld leer ist
    public static void notFilled(String name){
        String title = "Keine Angabe getroffen";
        String text = "Bitte geben Sie im Feld " + name + " etwas an";
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }
}
