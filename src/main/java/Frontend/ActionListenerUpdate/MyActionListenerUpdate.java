package Frontend.ActionListenerUpdate;

import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Komponenten.Interaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class MyActionListenerUpdate implements ActionListener{
    // Zum Speichern der Fehlermeldungen
    ArrayList<String> errorMessages = new ArrayList<>();

    // Von der aufrufenden Methode wird Btn übergeben und über den Konstruktor in der Variable gespeichert.
    private JButton Btn;
    public MyActionListenerUpdate(JButton Btn) {
        this.Btn = Btn;
    }

    // In Abhängigkeit vom ausgewählten Button eine andere Funktionen aufrufen
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = Btn.getText(); // Ermittlung Text vom Button
        if (buttonText == "OK") {
            ok();
        } else if (buttonText == "Abbrechen") {
            abbrechen();
        } else if (buttonText == "Zurück zu Start") {
            abbrechen();
            Cards.changeCard(Cards.nameStart);
        }
    }

    private void ok(){
        checkfields(); // Felder prüfen
        if(errorMessages.isEmpty()) { // Sofern keine Fehler vorliegen
            fillFields(); // Felder füllen
            if(checkChanged()){
                insertUpdateEinstellungen(); // insert durchführen
                Interaction.einstellungChanged(); // Meldung über erfolgreiches ändern
            } else {
                Interaction.einstellungNotChanged();
            }
            backToStart();
        } else {
            Checks.showError(errorMessages); // Ausgabe Fehlermeldung(en)
        }
    }

    private void abbrechen(){
        try{
            fillFields();
            // System.out.println("checkChanged: " + checkChanged());
        } catch (Exception e) {
            checkfields(); // Fehlermeldungen zu geprüften Feldern hochbringen
        }

        if (checkChanged()) {
            Boolean action = Interaction.abbrechen();
            Boolean stop = true;
            if(action == true) {
                checkfields();
                if(errorMessages.isEmpty()){
                    // Bei Ja die Daten updaten, sofern kein Fehler
                    insertUpdateEinstellungen();
                    Interaction.einstellungChanged(); // Meldung über erfolgreiches ändern
                } else {
                    Checks.showError(errorMessages); // Ausgabe Fehlermeldung(en)
                    stop = false;
                }
            }
            // Unabhängig von Ja, Nein oder X das Fenster schließen (sofern kein Fehler vorhanden)
            if(stop) {
                backToStart();
            }
        } else {
            backToStart();
        }
    }

    private void backToStart(){
        Cards.changeCard(Cards.nameEinstellungen);
    }

    // Zu implementierende Funktionen
    protected abstract void checkfields();
    protected abstract void fillFields();
    protected abstract Boolean checkChanged();
    protected abstract void insertUpdateEinstellungen();
}
