package Frontend.ActionListener;

import Frontend.Cards;
import Frontend.Komponenten.Interaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Frontend.Cards.cardLayout;

public abstract class MyActionListener implements ActionListener {
    // Zum Speichern der Fehlermeldungen
    ArrayList<String> errorMessages = new ArrayList<>();

    // Von der aufrufenden Methode wird Btn übergeben und über den Konstruktor in der Variable gespeichert.
    private JButton Btn;
    public MyActionListener(JButton Btn) {
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
            cardLayout.show(Cards.cardPanel, Cards.nameStart);
        }
    }

    public void ok(){
        boolean notInWork = !checkFieldsfilled(); // Prüfung, ob ein Feld gefüllt ist
        boolean noElement = checkElementInList(); // Prüfung, ob Element in Liste vorhanden ist
        System.out.println("Ergebnis Check - notInWork: " + notInWork + " und noElement: " + noElement);
        doCheckInsertBack(notInWork, noElement);
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
        showError(errorMessages); // Ausgabe Fehlermeldung(en)
        return inWork;
    }


    // TODO: einzelne Fälle prüfen
    public void doCheckInsertBack(Boolean notInWork, Boolean noElement){
        // Sind die Felder gefüllt, dann Elemente einfügen (wenn keine Fehlermeldung vorhanden)
        if(notInWork == false && noElement == true) {
            if(elementHinzu() == false){
                // Elemente aus Liste verarbeiten und Dialog schließen
                insertAndBack();
            }
        }
        // Sind die Felder nicht gefüllt, aber Elemente in der Liste, dann diese verarbeiten und Dialog schließen
        if(notInWork == false && noElement == false) {
            insertAndBack();
        }
    }

    public void insertAndBack() {
        elementInsert();
        // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
        backToStart();
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
        }
    }

    // Zu implementierende Funktionen
    protected abstract void checkFields();
    protected abstract void fillFields();
    protected abstract void nextChecks();
    protected abstract void elementInList();
    protected abstract boolean checkFieldsfilled();
    protected abstract boolean checkElementInList();
    protected abstract void elementInsert();
    protected abstract void backToStart();
}