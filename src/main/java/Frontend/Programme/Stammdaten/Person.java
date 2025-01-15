package Frontend.Programme.Stammdaten;

import Frontend.ActionListenerInsert.PersonListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;

public class Person extends JPanel{
    public static EingabePanel vorname = new EingabePanel("Vorname: ");
    public static EingabePanel nachname = new EingabePanel("Nachname: ");
    public static EingabePanel alter = new EingabePanel("Alter: ");

    static Buttons buttons = new Buttons();

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
        PersonListener erfassen = new PersonListener(buttons.create_btn) {
        };buttons.create_btn.addActionListener(erfassen);

        PersonListener ok = new PersonListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        PersonListener abbrechen = new PersonListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        PersonListener zurueckStart = new PersonListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}
