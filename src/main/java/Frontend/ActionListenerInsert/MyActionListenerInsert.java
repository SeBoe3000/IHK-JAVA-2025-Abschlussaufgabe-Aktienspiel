package Frontend.ActionListenerInsert;

import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Komponenten.Interaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class MyActionListenerInsert implements ActionListener {
    // Zum Speichern der Fehlermeldungen
    ArrayList<String> errorMessages = new ArrayList<>();

    // Von der aufrufenden Methode wird Btn übergeben und über den Konstruktor in der Variable gespeichert.
    private JButton Btn;
    public MyActionListenerInsert(JButton Btn) {
        this.Btn = Btn;
    }

    // In Abhängigkeit vom ausgewählten Button eine andere Funktionen aufrufen
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = Btn.getText(); // Ermittlung Text vom Button
        if (buttonText == "OK") {
            ok();
        } else if (buttonText == "Erfassen") {
            erfassen();
        }else if (buttonText == "Abbrechen") {
            abbrechen();
        } else if (buttonText == "Zurück zu Start") {
            abbrechen();
            Cards.changeCard(Cards.nameStart);
        }
    }

    public void ok(){
        boolean notInWork = !checkFieldsfilled(); // Prüfung, ob ein Feld gefüllt ist
        boolean noElement = checkElementInList(); // Prüfung, ob Element in Liste vorhanden ist
        System.out.println("Ergebnis Check - notInWork: " + notInWork + " und noElement: " + noElement);

        // Bei keiner Angabe und keinem Element - Fehlermeldungen hochbringen
        if(notInWork == true && noElement == true) {
            checkFields();
            Checks.showError(errorMessages); // Ausgabe Fehlermeldung(en)
        } else {
            doCheckInsertBack(notInWork, noElement);
        }
    }

    public void erfassen(){
        // Bei erfolgreichem Ausführen einen Hinweis auswerfen
        if (!elementHinzu()) {
            JOptionPane.showMessageDialog(null, "Element wurde der Liste hinzugefügt.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void abbrechen() {
        boolean notInWork = !checkFieldsfilled(); // Prüfung, ob 1 Feld gefüllt ist
        boolean noElement = checkElementInList(); // Prüfung, ob Element in Liste vorhanden ist
        System.out.println("Ergebnis Check - notInWork: " + notInWork + " und noElement: " + noElement);

        // Nachfrage, wenn mind. 1 Feld gefüllt oder noch ein Element in der Liste drin ist
        if(notInWork == false || noElement == false) {
            Boolean action = Interaction.abbrechen();
            if(action == false) {
                // Bei Nein oder X, Fenster schließen
                backToStart();
            } else {
                // Bei Ja die Daten verarbeiten und Fenster schließen
                doCheckInsertBack(notInWork, noElement);
            }
        }
        // Schließen, wenn keine Angabe und Element vorhanden
        if(notInWork == true && noElement == true) {
            backToStart();
        }
        //
    }

    protected boolean elementHinzu() {
        boolean inWork = true;
        checkFields(); // Eingaben prüfen
        if(errorMessages.isEmpty()) { // Sofern keine Fehler vorliegen
            fillFields(); // Felder füllen
            nextChecks(); // Prüfung, ob Element in Liste oder Datenbank vorhanden ist.
        }
        if (errorMessages.isEmpty()){ // Sofern weiterhin keine Fehler vorliegen
            elementInList(); // Element der Liste hinzufügen
            inWork = false;
        }
        Checks.showError(errorMessages); // Ausgabe Fehlermeldung(en)
        return inWork;
    }

    public void doCheckInsertBack(Boolean notInWork, Boolean noElement){
        Boolean next = true;

        // Sind die Felder gefüllt, dann Elemente einfügen (wenn keine Fehlermeldung vorhanden)
        if(notInWork == false) {
            if(elementHinzu() == true){
                next = false;
            } else {
                noElement = checkElementInList(); // noElement ändern, ansonsten wird insert nicht aufgerufen
            }
        }

        // Sind Elemente in der Liste, dann diese Einfügen
        if(noElement == false && next) {
            // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
            elementInsert();
        }

        if(next){
            backToStart();
        }
    }

    protected void backToStart(){
        // Arrayliste leeren
        clearliste();
        // Werte und Fehler in Feldern leeren, sonst sind diese beim nächsten Mal gefüllt
        felderLeeren();
        // Panel wechseln
        changePanel();
    }

    // Zu implementierende Funktionen
    protected abstract void checkFields();
    protected abstract void fillFields();
    protected abstract void nextChecks();
    protected abstract void elementInList();
    // Prüfung, ob ein Feld gefüllt sind (sobald eines leer ist, wird false übergeben.)
    protected abstract boolean checkFieldsfilled();
    protected abstract boolean checkElementInList();
    protected abstract void elementInsert();
    protected abstract void clearliste();
    // Feld leeren und Fehler entfernen
    protected abstract void felderLeeren();
    protected abstract void changePanel();
}