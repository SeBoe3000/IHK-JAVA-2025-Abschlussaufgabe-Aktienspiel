package Frontend.Programme.Bewegungsdaten;

import Frontend.Cards;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Frontend.Cards.cardLayout;

public class Wert extends JPanel {
    EingabePanel aktie = new EingabePanel("Aktie (ISIN): ");
    EingabePanel kurs = new EingabePanel("Kurs: ");
    EingabePanel kassenbestand = new EingabePanel("Kassenbestand: ");

    Buttons buttons = new Buttons();

    public Wert(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Aktie hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(aktie, gbc);

        // Kurs hinzufügen
        gbc.gridy = 1; // Zeile
        add(kurs, gbc);

        // Kassenbestand hinzufügen
        gbc.gridy = 2; // Zeile
        add(kassenbestand, gbc);

        // Buttons hinzufügen
        gbc.gridy = 3; // Zeile
        buttons.setVisibleFalse();
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {
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
                backToStart();

            }
        };
        buttons.cancel_btn.addActionListener(abbrechen);
    }

    // Prüfung, ob eine Feld gefüllt ist
    private boolean checkFilled(){
        Boolean checked = true;
        if(aktie.getTextfield().isEmpty() && kurs.getTextfield().isEmpty() && kassenbestand.getTextfield().isEmpty()){
            checked = false;
        }
        return checked;
    }

    // Felder leeren
    private void clearFields(){
        aktie.setTextField("");
        kurs.setTextField("");
        kassenbestand.setTextField("");
    }

    // Zu Start zurückkehren
    private void backToStart(){
        // Felder leeren
        clearFields();
        // Panel wechseln
        cardLayout.show(Cards.cardPanel, "panelStart");
    }
}