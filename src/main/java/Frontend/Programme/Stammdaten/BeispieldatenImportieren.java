package Frontend.Programme.Stammdaten;

import Backend.*;
import Datenbank.SQLAktien;
import Datenbank.SQLAktienverlauf;
import Datenbank.SQLKapitalverlauf;
import Datenbank.SQLPerson;
import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.Interaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BeispieldatenImportieren extends JPanel{
    static JCheckBox person_chb = new JCheckBox("Personen importieren");
    static JCheckBox aktie_chb = new JCheckBox("Aktien importieren");
    static JCheckBox startkapital_chb = new JCheckBox("Startkapital importieren");
    static JCheckBox startkurs_chb = new JCheckBox("Startkurse importieren");
    static JCheckBox kauf_chb = new JCheckBox("Käufe importieren");
    static JCheckBox wert_chb = new JCheckBox("Unternehmenswerte importieren");

    static Buttons buttons = new Buttons();

    // Zum Speichern der Fehlermeldungen
    public static ArrayList<String> errorMessages = new ArrayList<>();

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

        // Personen importieren hinzufügen
        add(person_chb, gbc);

        // Aktien importieren hinzufügen
        gbc.gridy = 1; // Zeile
        add(aktie_chb, gbc);

        // Startkapital importieren hinzufügen
        gbc.gridy = 2; // Zeile
        add(startkapital_chb, gbc);

        // Startkurse importieren hinzufügen
        gbc.gridy = 3; // Zeile
        add(startkurs_chb, gbc);

        // Käufe importieren hinzufügen
        gbc.gridy = 4; // Zeile
        kauf_chb.setVisible(false);
        add(kauf_chb, gbc);

        // Werte importieren hinzufügen
        gbc.gridy = 5; // Zeile
        wert_chb.setVisible(false);
        add(wert_chb, gbc);

        // Buttons hinzufügen
        gbc.gridy = 6; // Zeile
        buttons.setVisibleErfassenFalse();
        buttons.setVisibleStartFalse();
        add(buttons, gbc);

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
        buttons.cancel_btn.addActionListener(zurueck);

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
        buttons.ok_btn.addActionListener(importieren);
    }

    // Prüfung, ob eine Checkbox markiert ist
    private static boolean checkSelected(){
        Boolean checked = false;
        if(person_chb.isSelected() || aktie_chb.isSelected() || startkapital_chb.isSelected() ||
                startkurs_chb.isSelected() || kauf_chb.isSelected() || wert_chb.isSelected()){
            checked = true;
        }
        return checked;
    }

    // Import durchführen
    private static void importieren(){
        if(person_chb.isSelected()) {
            List<ElementPerson> PersonList = DatenDateiLesen.readPerson();
            if (!PersonList.isEmpty()){
                if (SQLPerson.selectInsertTablePerson(PersonList) == false) {
                    errorMessages.add("Beim Insert der Personen gab es einen Fehler.");
                }
            }
        }

        if(aktie_chb.isSelected()){
            List<ElementAktie> AktieList = DatenDateiLesen.readAktie();
            if(!AktieList.isEmpty()) {
                if (SQLAktien.selectInsertTableAktie(AktieList) == false) {
                    errorMessages.add("Beim Insert der Aktien gab es einen Fehler.");
                }
            }
        }

        if(startkapital_chb.isSelected()){
            List<ElementKapitalverlauf> StartkapitalList = DatenDateiLesen.readStartkapital();
            if(!StartkapitalList.isEmpty()){
                if (SQLKapitalverlauf.selectInsertTableKapitalverlauf(StartkapitalList) == false) {
                    errorMessages.add("Beim Insert vom Startkapital gab es einen Fehler.");
                }
            }
        }

        if(startkurs_chb.isSelected()){
            List<ElementAktienverlauf> StartkursList = DatenDateiLesen.readStartkurs();
            if(!StartkursList.isEmpty()){
                if (SQLAktienverlauf.selectInsertTableAktienverlauf(StartkursList) == false) {
                    errorMessages.add("Beim Insert vom Startkurs gab es einen Fehler.");
                }
            }
        }

        if(kauf_chb.isSelected()){
            // TODO: Umsetzen mit Prüfungen
        }

        if(wert_chb.isSelected()){
            // TODO: Umsetzen mit Prüfungen
        }

        if(errorMessages.isEmpty()){
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich importiert.");
            backToStart();
        } else {
            Checks.showError(errorMessages);
        }
    }

    // Checkboxen demarkieren
    private static void checkboxUnselect(){
        person_chb.setSelected(false);
        aktie_chb.setSelected(false);
        startkapital_chb.setSelected(false);
        startkurs_chb.setSelected(false);
        kauf_chb.setSelected(false);
        wert_chb.setSelected(false);
    }

    // Zu Start zurückkehren
    private static void backToStart(){
        // Panel wechseln
        Cards.changeCard(Cards.nameStart);
        // Checkboxen beim Zurückkehren demarkieren
        checkboxUnselect();
    }
}
