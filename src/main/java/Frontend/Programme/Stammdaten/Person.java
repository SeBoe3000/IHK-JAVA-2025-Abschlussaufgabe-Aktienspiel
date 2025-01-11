package Frontend.Programme.Stammdaten;

import Frontend.Cards;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Frontend.Cards.cardLayout;

public class Person extends JPanel{
    EingabePanel vorname = new EingabePanel("Vorname: ");
    EingabePanel nachname = new EingabePanel("Nachname: ");
    EingabePanel alter = new EingabePanel("Alter: ");

    Buttons buttons = new Buttons();

    public Person(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Person hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(vorname, gbc);

        // Nachname hinzufügen
        gbc.gridy = 1; // Zeile
        add(nachname, gbc);

        // Alter hinzufügen
        gbc.gridy = 2; // Zeile
        add(alter, gbc);

        // Buttons hinzufügen
        gbc.gridy = 3; // Zeile
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {

        ActionListener ok = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        };
        buttons.ok_btn.addActionListener(ok);

        ActionListener erfassen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        };
        buttons.create_btn.addActionListener(erfassen);

        ActionListener abbrechen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        };
        buttons.cancel_btn.addActionListener(abbrechen);

        ActionListener zurueckStart = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                // Panel wechseln
                cardLayout.show(Cards.cardPanel, "panelStart");
            }
        };
        buttons.backstart.addActionListener(zurueckStart);
    }
}
