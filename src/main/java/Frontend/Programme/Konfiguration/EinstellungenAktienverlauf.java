package Frontend.Programme.Konfiguration;

import Frontend.ActionListenerUpdate.EinstellungenAktienverlaufListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;

public class EinstellungenAktienverlauf extends JPanel{
    // Float minDividendeRunde = 0F;
    // Float maxDividendeRunde = 100000F;
    // Float minAktienkurs = 10F;

    public static EingabePanel minDividendeRunde = new EingabePanel("Minimale Dividende pro Runde: ");
    public static EingabePanel maxDividendeRunde = new EingabePanel("Maximale Dividende pro Runde: ");
    public static EingabePanel minAktienkurs = new EingabePanel("Minimaler Aktienkurs: ");

    static Buttons buttons = new Buttons();

    public EinstellungenAktienverlauf(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // minDividendeRunde hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(minDividendeRunde, gbc);

        // maxDividendeRunde hinzufügen
        gbc.gridy = 1; // Zeile
        add(maxDividendeRunde, gbc);

        // minAktienkurs hinzufügen
        gbc.gridy = 2; // Zeile
        add(minAktienkurs, gbc);

        // Buttons hinzufügen
        gbc.gridy = 3; // Zeile
        buttons.setVisibleErfassenFalse();
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }
    private void buttonListener() {
        EinstellungenAktienverlaufListener ok = new EinstellungenAktienverlaufListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        EinstellungenAktienverlaufListener abbrechen = new EinstellungenAktienverlaufListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        EinstellungenAktienverlaufListener zurueckStart = new EinstellungenAktienverlaufListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}
