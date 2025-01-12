package Frontend.ActionListener;

import Frontend.Komponenten.Interaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class MyAbbrechenListener implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    @Override
    public void actionPerformed(ActionEvent e) {
        // Prüfung ob 1 Feld gefüllt ist
        boolean notInWork = checkFieldsfilled();
        // Check ob Element in der Liste
        boolean noElement = !checkElemetInList();

        // System.out.println("Ergebnis Check - notInWork: " + notInWork + " und noElement: " + noElement);
        // Verarbeitung wenn mind. 1 Feld gefüllt oder noch ein Element in der Liste drin ist
        if(notInWork == false || noElement == false) {

            Boolean action = Interaction.abbrechen();
            if(action == false) {
                // Bei Nein oder X, Fenster schließen
                backToStart();
            } else {
                // Bei Ja Eingabencheck ausführen (nur wenn Felder gefüllt sind)
                if(notInWork == false) {
                    // Nur einfügen, wenn keine Fehlermeldung vorhanden
                    if(elementHinzu() == false){
                        elementInsert();
                        // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
                        backToStart();
                    }
                }
                if(noElement == false && notInWork == true) {
                    // Bei Ja Insert aufrufen und Dialog schließen
                    elementInsert();
                    // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
                    backToStart();
                }
            }
        }
    }
    protected abstract boolean checkFieldsfilled();
    protected abstract boolean checkElemetInList();
    protected abstract void elementInsert();
    protected abstract void backToStart();
    protected abstract boolean elementHinzu();
}
