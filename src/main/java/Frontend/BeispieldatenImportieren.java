package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BeispieldatenImportieren {
    public static final JFrame importExample = new JFrame("Beispieldaten importieren");

    static JCheckBox person_chb = new JCheckBox("Personen importieren");
    static JCheckBox aktie_chb = new JCheckBox("Aktien importieren");
    static JCheckBox startkapital_chb = new JCheckBox("Startkapital importieren");
    static JCheckBox startkurs_chb = new JCheckBox("Startkurse importieren");

    JButton create = new JButton("Daten Importieren");
    JButton back = new JButton("Zurück zum Start");

    private void beispieldatenImportieren() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Checkboxen hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        panel.add(person_chb, gbc);

        gbc.gridy = 1; // Zeile
        panel.add(aktie_chb, gbc);

        gbc.gridy = 2; // Zeile
        panel.add(startkapital_chb, gbc);

        gbc.gridy = 3; // Zeile
        panel.add(startkurs_chb, gbc);

        // Import hinzufügen
        gbc.gridy = 4; // Zeile
        panel.add(create, gbc);

        // Zurück hinzufügen
        gbc.gridy = 5; // Zeile
        panel.add(back, gbc);

        // Panel dem Frame hinzufügen
        importExample.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        importExample.setSize(dim.width / 2, dim.height / 2);
        importExample.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        importExample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        importExample.setVisible(true);
    }

    private void buttonListenerstart() {

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
        Start.start.setVisible(true);
        importExample.setVisible(false);
        // Checkboxen beim Zurückkehren demarkieren
        checkboxUnselect();
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                beispieldatenImportieren();
                buttonListenerstart();
            }
        });
    }
}
