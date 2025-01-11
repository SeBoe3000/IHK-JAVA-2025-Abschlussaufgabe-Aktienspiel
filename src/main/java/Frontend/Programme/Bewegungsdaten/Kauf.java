package Frontend.Programme.Bewegungsdaten;

import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Kauf extends JPanel{
    EingabePanel person = new EingabePanel("Person (ID): ");
    EingabePanel aktie = new EingabePanel("Aktie: ");
    EingabePanel anzahl = new EingabePanel("Anzahl Aktien: ");
    EingabePanel restwert = new EingabePanel("Restwert: ");

    Buttons buttons = new Buttons();

    public Kauf(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Person hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(person, gbc);

        // Aktie hinzufügen
        gbc.gridy = 1; // Zeile
        add(aktie, gbc);

        // Anzahl hinzufügen
        gbc.gridy = 2; // Zeile
        add(anzahl, gbc);

        // Restwert hinzufügen
        gbc.gridy = 3; // Zeile
        add(restwert, gbc);

        // Buttons hinzufügen
        gbc.gridy = 4; // Zeile
        buttons.setVisibleFalse();
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener(cardLayout, cardPanel);
    }

    private void buttonListener(CardLayout cardLayout, JPanel cardPanel) {
        ActionListener ok = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Prüfen, ob Element in der Liste vorhanden ist
                // TODO: Prüfen, ob Element in der Datenbank vorhanden ist
                // TODO: Element der Liste hinzufügen
            }
        };
        buttons.ok_btn.addActionListener(ok);

        ActionListener create = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Prüfen, ob Element in der Liste vorhanden ist
                // TODO: Prüfen, ob Element in der Datenbank vorhanden ist
                // TODO: Element Liste hinzufügen
                // TODO: Element aus Liste in Datenbank übertragen
                // TODO: Programmschließen
            }
        };
        buttons.create_btn.addActionListener(create);

        ActionListener abbrechen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Logik einbauen
                backToStart(cardLayout, cardPanel);

            }
        };
        buttons.cancel_btn.addActionListener(abbrechen);


    }

    // Prüfung, ob ein Feld gefüllt ist
    private boolean checkFilled(){
        Boolean checked = true;
        if(person.getTextfield().isEmpty() && aktie.getTextfield().isEmpty() && anzahl.getTextfield().isEmpty()){
            checked = false;
        }
        return checked;
    }

    // Felder leeren
    private void clearFields(){
        person.setTextField("");
        aktie.setTextField("");
        anzahl.setTextField("");
    }

    // Zu Start zurückkehren
    private void backToStart(CardLayout cardLayout, JPanel cardPanel){
        // Felder leeren
        clearFields();
        // Panel wechseln
        cardLayout.show(cardPanel, "panelStart");
    }

    /*public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                kauf();
                buttonListenerstart();
            }
        });
    }

     */
}