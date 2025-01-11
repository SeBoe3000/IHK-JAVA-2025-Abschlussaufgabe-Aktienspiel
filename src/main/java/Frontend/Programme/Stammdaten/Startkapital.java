package Frontend.Programme.Stammdaten;

import Frontend.Cards;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Frontend.Cards.cardLayout;

public class Startkapital extends JPanel{
    EingabePanel person = new EingabePanel("Person (ID): ");
    EingabePanel betrag = new EingabePanel("Betrag: ");

    Buttons buttons = new Buttons();

    public Startkapital(CardLayout cardLayout, JPanel cardPanel) {
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

        // Betrag hinzufügen
        gbc.gridy = 1; // Zeile
        add(betrag, gbc);

        // Buttons hinzufügen
        gbc.gridy = 2; // Zeile
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
