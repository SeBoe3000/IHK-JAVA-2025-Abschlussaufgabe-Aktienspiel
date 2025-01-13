package Frontend.Programme.Stammdaten;

import Frontend.ActionListener.StartkapitalListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;

public class Startkapital extends JPanel{
    public static EingabePanel person = new EingabePanel("Person (ID): ");
    public static EingabePanel betrag = new EingabePanel("Betrag: ");

    static Buttons buttons = new Buttons();

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
        StartkapitalListener erfassen = new StartkapitalListener(buttons.create_btn) {
        };buttons.create_btn.addActionListener(erfassen);

        StartkapitalListener ok = new StartkapitalListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        StartkapitalListener abbrechen = new StartkapitalListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        StartkapitalListener zurueckStart = new StartkapitalListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}
