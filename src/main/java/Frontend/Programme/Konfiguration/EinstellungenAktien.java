package Frontend.Programme.Konfiguration;

import Frontend.ActionListenerUpdate.EinstellungenAktienListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;

public class EinstellungenAktien extends JPanel{
    //Integer maxAnzahlAktien = 100;

    public static EingabePanel maxAnzahlAktien = new EingabePanel("Maximale Anzahl Aktien pro Unternehmen: ");

    static Buttons buttons = new Buttons();

    public EinstellungenAktien(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // maxAnzahlAktien hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(maxAnzahlAktien, gbc);

        // Buttons hinzufügen
        gbc.gridy = 1; // Zeile
        buttons.setVisibleErfassenFalse();
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {
        EinstellungenAktienListener ok = new EinstellungenAktienListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        EinstellungenAktienListener abbrechen = new EinstellungenAktienListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        EinstellungenAktienListener zurueckStart = new EinstellungenAktienListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}
