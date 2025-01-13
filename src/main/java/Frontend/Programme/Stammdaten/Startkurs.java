package Frontend.Programme.Stammdaten;

import Frontend.ActionListener.StartkursListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;

public class Startkurs extends JPanel{
    public static EingabePanel aktie = new EingabePanel("Aktie (ISIN): ");
    public static EingabePanel kurs = new EingabePanel("Startkurs: ");

    static Buttons buttons = new Buttons();

    public Startkurs(CardLayout cardLayout, JPanel cardPanel) {
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

        // Buttons hinzufügen
        gbc.gridy = 2; // Zeile
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {
        StartkursListener erfassen = new StartkursListener(buttons.create_btn) {
        };buttons.create_btn.addActionListener(erfassen);

        StartkursListener ok = new StartkursListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        StartkursListener abbrechen = new StartkursListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        StartkursListener zurueckStart = new StartkursListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}
