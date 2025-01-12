package Frontend.ActionListener;

import Frontend.Cards;
import Frontend.Komponenten.Interaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Frontend.Cards.cardLayout;

public abstract class MyActionListener implements ActionListener {
    // Von der aufrufenden Methode wird Btn übergeben und über den Konstruktor in der Variable gespeichert.
    private JButton Btn;
    public MyActionListener(JButton Btn) {
        this.Btn = Btn;
    }

    // In Abhängigkeit vom ausgewählten Button andere Funktionen aufrufen
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
            // Abbrechen ausführen
            cardLayout.show(Cards.cardPanel, "panelStart");
        }
    }

    public void ok(){
        boolean notInWork = !checkFieldsfilled(); // Prüfung, ob ein Feld gefüllt ist
        boolean noElement = checkElemetInList(); // Prüfung, ob Element in Liste vorhanden ist
        System.out.println("Ergebnis Check - notInWork: " + notInWork + " und noElement: " + noElement);
        checkInsertBack(notInWork, noElement);
    }

    public void erfassen(){
        // Bei erfolgreichem Ausführen einen Hinweis auswerfen
        if (!elementHinzu()) {
            JOptionPane.showMessageDialog(null, "Element wurde der Liste hinzugefügt.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void abbrechen() {
        boolean notInWork = !checkFieldsfilled(); // Prüfung, ob 1 Feld gefüllt ist
        boolean noElement = checkElemetInList(); // Prüfung, ob Element in Liste vorhanden ist
        System.out.println("Ergebnis Check - notInWork: " + notInWork + " und noElement: " + noElement);

        // Nachfrage, wenn mind. 1 Feld gefüllt oder noch ein Element in der Liste drin ist
        if(notInWork == false || noElement == false) {
            Boolean action = Interaction.abbrechen();
            if(action == false) {
                // Bei Nein oder X, Fenster schließen
                backToStart();
            } else {
                // Bei Ja die Daten verarbeiten und Fenster schließen
                checkInsertBack(notInWork, noElement);
            }
        }
    }

    public void checkInsertBack(Boolean notInWork, Boolean noElement){
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

    // Funktionen, die implementiert werden müssen
    protected abstract boolean checkFieldsfilled();
    protected abstract boolean checkElemetInList();
    protected abstract void elementInsert();
    protected abstract void backToStart();
    protected abstract boolean elementHinzu();
}
