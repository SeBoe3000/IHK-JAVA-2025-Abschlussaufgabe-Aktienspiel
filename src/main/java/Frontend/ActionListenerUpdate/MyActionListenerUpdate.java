package Frontend.ActionListenerUpdate;

import Frontend.Cards;

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

    public void ok(){
        // TODO: Inserts durchführen
    }

    public void abbrechen(){
        // TODO: Prüfung Änderung vorgenommen und wenn ja, dann fragen, ob abspeichern.
        Cards.changeCard(Cards.nameEinstellungen);
    }
}
