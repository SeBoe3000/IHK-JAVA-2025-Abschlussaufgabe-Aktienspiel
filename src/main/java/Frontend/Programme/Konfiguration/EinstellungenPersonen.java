package Frontend.Programme.Konfiguration;

import Frontend.ActionListenerUpdate.EinstellungenPersonenListener;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EinstellungenPersonen extends JPanel{
    public static EingabePanel defaultStrartkapital = new EingabePanel("Default Startkapital: ");
    public static JCheckBox defaultStartkapitalBearbeitbar = new JCheckBox("Default bearbeitbar?");

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

        // Checkbox hinzufügen
        gbc.gridy = 1; // Zeile
        add(defaultStartkapitalBearbeitbar, gbc);

        // Buttons hinzufügen
        gbc.gridy = 2; // Zeile
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

        // Beim Ändern der Checkbox das Startkapital sperren/freigeben
        ActionListener ausgrauenDefaultStartkapital = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bearbeitenStartkapital();
            }
        };
        defaultStartkapitalBearbeitbar.addActionListener(ausgrauenDefaultStartkapital);

        /* Andere Schreibweise:
        defaultStartkapitalBearbeitbar.addActionListener(e -> {
            bearbeitenStartkapital();
        }); */
    }

    public static void bearbeitenStartkapital(){
        if (defaultStartkapitalBearbeitbar.isSelected()) {
            defaultStrartkapital.setEnabledTrue();
        } else {
            defaultStrartkapital.setEnabledFalse();
        }
    }
}
