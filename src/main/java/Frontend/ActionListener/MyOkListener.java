package Frontend.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class MyOkListener implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    @Override
    public void actionPerformed(ActionEvent e) {
        // Prüfung, ob ein Feld gefüllt ist
        boolean notInWork = checkFieldsfilled();

        // Check ob Element in Liste vorhanden
        boolean noElement = !checkElemetInList();

        // bei leeren Feldern und vorhandenem Element in der Liste den Insert durchführen und den Dialog schließen
        if(notInWork == true && noElement == false){
            elementInsert();
            // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
            backToStart();
        }

        // ist mind. 1 Feld gefüllt, dann nur wenn inWork false ist, die Datensätze in die Datenbank schreiben.
        else if(elementHinzu() == false){
            elementInsert();
            // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
            backToStart();
        }
    }


    protected abstract boolean checkFieldsfilled();
    protected abstract boolean checkElemetInList();
    protected abstract void elementInsert();
    protected abstract void backToStart();
    protected abstract boolean elementHinzu();
}
