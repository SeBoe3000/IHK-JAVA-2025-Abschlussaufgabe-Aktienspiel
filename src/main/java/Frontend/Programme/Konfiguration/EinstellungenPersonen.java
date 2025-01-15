package Frontend.Programme.Konfiguration;

import Frontend.ActionListenerUpdate.EinstellungenPersonenListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;

public class EinstellungenPersonen extends JPanel{
    public static EingabePanel defaultStrartkapital = new EingabePanel("Default Startkapital: ");

    static Buttons buttons = new Buttons();

    public EinstellungenPersonen(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // defaultStrartkapital hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(defaultStrartkapital, gbc);

        // Buttons hinzufügen
        gbc.gridy = 1; // Zeile
        buttons.setVisibleErfassenFalse();
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }
    private void buttonListener() {
        EinstellungenPersonenListener ok = new EinstellungenPersonenListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        EinstellungenPersonenListener abbrechen = new EinstellungenPersonenListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        EinstellungenPersonenListener zurueckStart = new EinstellungenPersonenListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);
    }
}
