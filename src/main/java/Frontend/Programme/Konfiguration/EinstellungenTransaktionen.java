package Frontend.Programme.Konfiguration;

import Frontend.ActionListenerUpdate.EinstellungenTransaktionenListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;
import Frontend.Komponenten.EingabePanelVonBis;

import javax.swing.*;
import java.awt.*;

public class EinstellungenTransaktionen extends JPanel{
    // Integer maxAktienPersonRunde = 30;
    // Integer minPersonRunde = 3;
    // Integer maxPersonRunde = 14;
    // Integer minAktieRunde = 3;
    // Integer maxAktieRunde = 14;
    // Float firstDividende = 30F;
    // Float secondDividende = 10F;

    public static EingabePanel maxAktienPersonRunde = new EingabePanel("Maximale Aktien pro Person und Runde: ");
    public static EingabePanelVonBis personRunde = new EingabePanelVonBis("Personen pro Runde: ");
    public static EingabePanelVonBis aktieRunde = new EingabePanelVonBis("Unternehmen pro Runde: ");
    public static EingabePanel firstDividende = new EingabePanel("Prozentuale Dividende erster Platz: ");
    public static EingabePanel secondDividende = new EingabePanel("Prozentuale Dividende zweiter Platz: ");

    static Buttons buttons = new Buttons();

    public EinstellungenTransaktionen(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // maxAktienPersonRunde hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(maxAktienPersonRunde, gbc);

        // PersonRunde hinzufügen
        gbc.gridy = 1; // Zeile
        add(personRunde, gbc);

        // aktieRunde hinzufügen
        gbc.gridy = 2; // Zeile
        add(aktieRunde, gbc);

        // firstDividende hinzufügen
        gbc.gridy = 3; // Zeile
        add(firstDividende, gbc);

        // secondDividende hinzufügen
        gbc.gridy = 4; // Zeile
        add(secondDividende, gbc);

        // Buttons hinzufügen
        gbc.gridy = 5; // Zeile
        buttons.setVisibleErfassenFalse();
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }
    private void buttonListener() {
        EinstellungenTransaktionenListener ok = new EinstellungenTransaktionenListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        EinstellungenTransaktionenListener abbrechen = new EinstellungenTransaktionenListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        EinstellungenTransaktionenListener zurueckStart = new EinstellungenTransaktionenListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}
