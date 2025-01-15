package Frontend.Programme.Bewegungsdaten;

import Frontend.ActionListenerInsert.KaufListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;

public class Kauf extends JPanel{
    public static EingabePanel person = new EingabePanel("Person (ID): ");
    public static EingabePanel aktie = new EingabePanel("Aktie: ");
    public static EingabePanel anzahl = new EingabePanel("Anzahl Aktien: ");
    public static EingabePanel restwert = new EingabePanel("Restwert: ");

    static Buttons buttons = new Buttons();

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
        // Restwert ausgrauen, da Anzeigefeld
        restwert.setEnabledFalse();

        // Buttons hinzufügen
        gbc.gridy = 4; // Zeile
        buttons.setVisibleStartFalse();
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {
        KaufListener erfassen = new KaufListener(buttons.create_btn) {
        };buttons.create_btn.addActionListener(erfassen);

        KaufListener ok = new KaufListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        KaufListener abbrechen = new KaufListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        KaufListener zurueckStart = new KaufListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}