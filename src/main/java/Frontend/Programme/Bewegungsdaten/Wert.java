package Frontend.Programme.Bewegungsdaten;

import Frontend.ActionListener.WertListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;

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
        WertListener erfassen = new WertListener(buttons.create_btn) {
        };buttons.create_btn.addActionListener(erfassen);

        WertListener ok = new WertListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        WertListener abbrechen = new WertListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        WertListener zurueckStart = new WertListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}