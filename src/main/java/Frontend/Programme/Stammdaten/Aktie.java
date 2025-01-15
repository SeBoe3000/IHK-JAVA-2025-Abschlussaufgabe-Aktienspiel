package Frontend.Programme.Stammdaten;

import Frontend.ActionListenerInsert.AktienListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;

public class Aktie extends JPanel{
    public static EingabePanel isin = new EingabePanel("ISIN: ");
    public static EingabePanel name = new EingabePanel("Name der Aktie: ");

    static Buttons buttons = new Buttons();

    public Aktie(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // ISIN hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(isin, gbc);

        // Name hinzufügen
        gbc.gridy = 1; // Zeile
        add(name, gbc);

        // Buttons hinzufügen
        gbc.gridy = 2; // Zeile
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {
        AktienListener erfassen = new AktienListener(buttons.create_btn) {
        };buttons.create_btn.addActionListener(erfassen);

        AktienListener ok = new AktienListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        AktienListener abbrechen = new AktienListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        AktienListener zurueckStart = new AktienListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}
