package Frontend.Programme.Stammdaten;

import Frontend.Cards;
import Frontend.Komponenten.Interaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Frontend.Cards.cardLayout;

public class BeispieldatenImportieren extends JPanel{
    static JCheckBox person_chb = new JCheckBox("Personen importieren");
    static JCheckBox aktie_chb = new JCheckBox("Aktien importieren");
    static JCheckBox startkapital_chb = new JCheckBox("Startkapital importieren");
    static JCheckBox startkurs_chb = new JCheckBox("Startkurse importieren");

    JButton create = new JButton("Daten Importieren");
    JButton back = new JButton("Zurück zum Start");

    public BeispieldatenImportieren(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Checkboxen hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        add(person_chb, gbc);

        gbc.gridy = 1; // Zeile
        add(aktie_chb, gbc);

        gbc.gridy = 2; // Zeile
        add(startkapital_chb, gbc);

        gbc.gridy = 3; // Zeile
        add(startkurs_chb, gbc);

        // Import hinzufügen
        gbc.gridy = 4; // Zeile
        add(create, gbc);

        // Zurück hinzufügen
        gbc.gridy = 5; // Zeile
        add(back, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {

        ActionListener zurueck = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkSelected()){
                    Boolean action = Interaction.abbrechen();
                    if(action) {
                        importieren();
                    }
                }
                backToStart();
            }
        };
        back.addActionListener(zurueck);

        ActionListener importieren = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Meldung ausgeben, wenn keine Checkbox markiert ist.
                if(checkSelected() == false) {
                    Interaction.nothingMarked();
                } else {
                    importieren();
                    checkboxUnselect();
                }
            }
        };
        create.addActionListener(importieren);
    }

    // Prüfung, ob eine Checkbox markiert ist
    private static boolean checkSelected(){
        Boolean checked = false;
        if(person_chb.isSelected() || aktie_chb.isSelected() || startkapital_chb.isSelected() || startkurs_chb.isSelected()){
            checked = true;
        }
        return checked;
    }

    // Import durchführen
    private static void importieren(){
        System.out.println("Imports durchführen");

        // Imports durchführen
        if(person_chb.isSelected()){
            // TODO: Prüfung Daten korrekt und nicht vorhanden
            // TODO: Daten importieren
            // TODO: Meldung ausgeben (Daten vorhanden oder erfolgreich verarbeitet)
        }

        if(aktie_chb.isSelected()){
            // TODO: Prüfung Daten korrekt und nicht vorhanden
            // TODO: Daten importieren
            // TODO: Meldung ausgeben (Daten vorhanden oder erfolgreich verarbeitet)
        }

        if(startkapital_chb.isSelected()){
            // TODO: Prüfung Daten korrekt und nicht vorhanden
            // TODO: Prüfung Schlüssel vorhanden
            // TODO: Daten importieren
            // TODO: Meldung ausgeben (Daten vorhanden oder erfolgreich verarbeitet)
        }

        if(startkurs_chb.isSelected()){
            // TODO: Prüfung Daten korrekt und nicht vorhanden
            // TODO: Prüfung Schlüssel vorhanden
            // TODO: Daten importieren
            // TODO: Meldung ausgeben (Daten vorhanden oder erfolgreich verarbeitet)
        }
    }

    // Checkboxen demarkieren
    private static void checkboxUnselect(){
        person_chb.setSelected(false);
        aktie_chb.setSelected(false);
        startkapital_chb.setSelected(false);
        startkurs_chb.setSelected(false);
    }

    // Zu Start zurückkehren
    private static void backToStart(){
        // Panel wechseln
        cardLayout.show(Cards.cardPanel, "panelStart");
        // Checkboxen beim Zurückkehren demarkieren
        checkboxUnselect();
    }
}