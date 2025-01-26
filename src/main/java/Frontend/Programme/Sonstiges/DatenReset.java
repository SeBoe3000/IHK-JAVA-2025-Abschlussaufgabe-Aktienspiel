package Frontend.Programme.Sonstiges;

import Datenbank.SQL;
import Datenbank.SQLEinstellungen;
import Datenbank.SQLSpiel;
import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.Interaction;
import Frontend.Programme.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DatenReset extends JPanel {
    JLabel runde = new JLabel("Runde:");
    static JCheckBox rundeAll_chb = new JCheckBox("Alle löschen");
    static JCheckBox rundeLast_chb = new JCheckBox("Letzte löschen");
    static JCheckBox rundeOpen_chb = new JCheckBox("Letzte öffnen");

    JLabel rundeLast = new JLabel("Von letzter Runde löschen:");
    static JCheckBox kauf_chb = new JCheckBox("Käufe");
    static JCheckBox wert_chb = new JCheckBox("Unternehmenswerte");

    static JCheckBox all_chb = new JCheckBox("Alle Daten löschen");
    static JCheckBox person_chb = new JCheckBox("Personen löschen");
    static JCheckBox startkapital_chb = new JCheckBox("Startkapital löschen");
    static JCheckBox aktie_chb = new JCheckBox("Aktien löschen");
    static JCheckBox startkurs_chb = new JCheckBox("Startkurse löschen");

    static Buttons buttons = new Buttons();

    // Zum Speichern der Fehlermeldungen
    public static ArrayList<String> errorMessages = new ArrayList<>();


    public DatenReset(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Checkboxen hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        // Runden löschen
        JPanel group_runde = new JPanel();
        group_runde.setLayout(new BoxLayout(group_runde, BoxLayout.X_AXIS));
        group_runde.add(runde);
        group_runde.add(rundeAll_chb);
        group_runde.add(rundeLast_chb);
        group_runde.add(rundeOpen_chb);
        add(group_runde, gbc);

        // Daten letzte Runde löschen
        gbc.gridy = 1; // Zeile

        JPanel group_letzteRunde = new JPanel();
        group_letzteRunde.setLayout(new BoxLayout(group_letzteRunde, BoxLayout.X_AXIS));
        group_letzteRunde.add(rundeLast);
        group_letzteRunde.add(kauf_chb);
        group_letzteRunde.add(wert_chb);
        add(group_letzteRunde, gbc);

        // Alles löschen
        gbc.gridy = 2; // Zeile
        add(all_chb, gbc);

        // Personen löschen
        gbc.gridy = 4; // Zeile

        JPanel group_personen = new JPanel();
        group_personen.setLayout(new BoxLayout(group_personen, BoxLayout.X_AXIS));
        group_personen.add(person_chb);
        group_personen.add(startkapital_chb);
        add(group_personen, gbc);

        // Aktien löschen
        gbc.gridy = 5; // Zeile

        JPanel group_aktien = new JPanel();
        group_aktien.setLayout(new BoxLayout(group_aktien, BoxLayout.X_AXIS));
        group_aktien.add(aktie_chb);
        group_aktien.add(startkurs_chb);
        add(group_aktien, gbc);

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
                        delete();
                    }
                }
                backToStart();
            }
        };
        buttons.cancel_btn.addActionListener(zurueck);

        ActionListener delete = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Meldung ausgeben, wenn keine Checkbox markiert ist.
                if(checkSelected() == false) {
                    Interaction.nothingMarked();
                } else {
                    delete();
                    checkboxUnselect();
                }
            }
        };
        buttons.ok_btn.addActionListener(delete);


        // Beim Ändern der Checkboxen abhängige Checkboxen (de)markieren

        // Alle Löschen markiert --> Letzte löschen, Wert, Kauf, Letzte öffnen demarkieren
        // Alle Löschen demarkiert --> Alle Daten löschen demarkieren
        ActionListener alleRunden = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rundeAll_chb.isSelected()) {
                    rundeLast_chb.setSelected(false);
                    kauf_chb.setSelected(false);
                    wert_chb.setSelected(false);
                    rundeOpen_chb.setSelected(false);

                } else {
                    all_chb.setSelected(false);
                }
            }
        };
        rundeAll_chb.addActionListener(alleRunden);

        // Letzte Löschen markiert --> Kauf und Wert markieren, Alle löschen, Letzte Öffnen, Alle Daten löschen, Aktien, Personen, Startkurs, Startkapital demarkieren
        // Letzte Löschen demarkiert --> Kauf und Wert demarkieren, wenn beide markiert waren.
        ActionListener letzteRunde = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rundeLast_chb.isSelected()) {
                    kauf_chb.setSelected(true);
                    wert_chb.setSelected(true);
                    rundeAll_chb.setSelected(false);
                    rundeOpen_chb.setSelected(false);
                    all_chb.setSelected(false);
                    person_chb.setSelected(false);
                    startkapital_chb.setSelected(false);
                    aktie_chb.setSelected(false);
                    startkurs_chb.setSelected(false);
                } else if (!rundeLast_chb.isSelected() && kauf_chb.isSelected() && wert_chb.isSelected()) {
                    kauf_chb.setSelected(false);
                    wert_chb.setSelected(false);
                }
            }
        };
        rundeLast_chb.addActionListener(letzteRunde);

        // Letzte Öffnen markiert --> alles demarkieren
        ActionListener letzteOpen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rundeOpen_chb.isSelected()) {
                    rundeAll_chb.setSelected(false);
                    rundeLast_chb.setSelected(false);
                    kauf_chb.setSelected(false);
                    wert_chb.setSelected(false);
                    all_chb.setSelected(false);
                    aktie_chb.setSelected(false);
                    person_chb.setSelected(false);
                    startkapital_chb.setSelected(false);
                    startkurs_chb.setSelected(false);
                }
            }
        };
        rundeOpen_chb.addActionListener(letzteOpen);

        // Letzter Wert oder Letzter Kauf markiert --> Alle löschen, Letzte öffnen, Alle Daten löschen, Personen, Aktien, Startkapital, Startkurs demarkieren.
        ActionListener letzteKaufOrWert = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(kauf_chb.isSelected() || wert_chb.isSelected()) {
                    rundeAll_chb.setSelected(false);
                    rundeOpen_chb.setSelected(false);
                    all_chb.setSelected(false);
                    person_chb.setSelected(false);
                    startkapital_chb.setSelected(false);
                    aktie_chb.setSelected(false);
                    startkurs_chb.setSelected(false);
                }
            }
        };
        kauf_chb.addActionListener(letzteKaufOrWert);
        wert_chb.addActionListener(letzteKaufOrWert);

        // Alle Daten löschen markiert --> Runde, Person, Startkapital, Aktie, Startkurs markieren und Letzte löschen, Letzte öffnen, Wert, Kauf demarkieren
        // Alle Daten löschen demarkiert --> Runde, Person, Startkapital, Aktie, Startkurs demarkieren, wenn alle markiert waren
        ActionListener alles = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(all_chb.isSelected()) {
                    rundeAll_chb.setSelected(true);
                    person_chb.setSelected(true);
                    startkapital_chb.setSelected(true);
                    aktie_chb.setSelected(true);
                    startkurs_chb.setSelected(true);
                    rundeLast_chb.setSelected(false);
                    wert_chb.setSelected(false);
                    kauf_chb.setSelected(false);
                    rundeOpen_chb.setSelected(false);
                } else if (!all_chb.isSelected() && rundeAll_chb.isSelected() && person_chb.isSelected() &&
                        startkapital_chb.isSelected() && aktie_chb.isSelected() && startkurs_chb.isSelected()) {
                    rundeAll_chb.setSelected(false);
                    person_chb.setSelected(false);
                    startkapital_chb.setSelected(false);
                    aktie_chb.setSelected(false);
                    startkurs_chb.setSelected(false);
                }
            }
        };
        all_chb.addActionListener(alles);

        // Person markiert --> Startkapital markieren, Alle löschen, Letzte Löschen, Kauf, Wert und Letzte Öffnen demarkieren
        // Person demarkiert --> Alle Daten Löschen demarkieren.
        ActionListener person = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(person_chb.isSelected()) {
                    startkapital_chb.setSelected(true);
                    rundeAll_chb.setSelected(false);
                    rundeOpen_chb.setSelected(false);
                    rundeLast_chb.setSelected(false);
                    kauf_chb.setSelected(false);
                    wert_chb.setSelected(false);
                } else {
                    all_chb.setSelected(false);
                }
            }
        };
        person_chb.addActionListener(person);

        // Startkapital demarkiert --> Person demarkieren. Alle Daten löschen demarkieren
        // Spartkapital markiert --> Alle löschen, Letzte Löschen, Kauf, Wert und Letzte Öffnen demarkieren
        ActionListener startkapital = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!startkapital_chb.isSelected()) {
                    person_chb.setSelected(false);
                    all_chb.setSelected(false);
                } else {
                    rundeAll_chb.setSelected(false);
                    rundeOpen_chb.setSelected(false);
                    rundeLast_chb.setSelected(false);
                    kauf_chb.setSelected(false);
                    wert_chb.setSelected(false);
                }
            }
        };
        startkapital_chb.addActionListener(startkapital);

        // Aktie markiert --> Startkurs markieren, Alle löschen, Letzte Löschen, Kauf, Wert und Letzte Öffnen demarkieren
        // Aktie demarkiert --> Alle Daten löschen demarkieren
        ActionListener aktie = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(aktie_chb.isSelected()) {
                    startkurs_chb.setSelected(true);
                    rundeAll_chb.setSelected(false);
                    rundeOpen_chb.setSelected(false);
                    rundeLast_chb.setSelected(false);
                    kauf_chb.setSelected(false);
                    wert_chb.setSelected(false);
                } else {
                    all_chb.setSelected(false);
                }
            }
        };
        aktie_chb.addActionListener(aktie);

        // Startkurs demarkiert --> Aktie demarkieren. Alle Daten Löschen demarkieren
        // Startkurs markiert --> Alle löschen, Letzte Löschen, Kauf, Wert und Letzte Öffnen demarkieren
        ActionListener startkurs = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!startkurs_chb.isSelected()) {
                    aktie_chb.setSelected(false);
                    all_chb.setSelected(false);
                } else {
                    rundeAll_chb.setSelected(false);
                    rundeOpen_chb.setSelected(false);
                    rundeLast_chb.setSelected(false);
                    kauf_chb.setSelected(false);
                    wert_chb.setSelected(false);
                }
            }
        };
        startkurs_chb.addActionListener(startkurs);
    }

    // Prüfung, ob eine Checkbox markiert ist
    private static boolean checkSelected(){
        Boolean checked = false;
        if(rundeAll_chb.isSelected() || rundeLast_chb.isSelected() || rundeOpen_chb.isSelected() ||
                kauf_chb.isSelected() || wert_chb.isSelected() || all_chb.isSelected() ||
                person_chb.isSelected() || startkapital_chb.isSelected() ||
                aktie_chb.isSelected() || startkurs_chb.isSelected()){
            checked = true;
        }
        return checked;
    }

    // Daten löschen
    private static void delete(){
        if(rundeAll_chb.isSelected()) {
            // Kapitalverlauf mit der Runde größer 0 löschen
            SQL.table("DELETE FROM Kapitalverlauf WHERE Runde > 0");
            // Aktienverlauf mit der Runde größer 0 löschen
            SQL.table("DELETE FROM Aktienverlauf WHERE Runde > 0");
            // Transaktionen löschen
            SQL.table("DELETE FROM Transaktionen WHERE Runde > 0");
            // Spielstand löschen
            SQL.table("DELETE FROM Spielstand");
            // Einstellung Runde auf 1 zurücksetzen
            SQLEinstellungen.setEinstellung("RND", "1");
        }

        if(rundeOpen_chb.isSelected()){
            // Runden ermitteln
            Integer rundeEinstellung = Start.getAktuelleRunde();
            Integer rundeTransaktionen = SQLSpiel.getOneInteger("SELECT Runde FROM Transaktionen WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen) LIMIT 1");
            Integer rundeAktienverlauf = SQLSpiel.getOneInteger("SELECT Runde FROM Aktienverlauf WHERE Runde = (SELECT MAX(Runde) FROM Aktienverlauf) LIMIT 1");
            // Prüfung, ob Runde abgeschlossen
            if(rundeEinstellung == rundeTransaktionen && rundeEinstellung == rundeAktienverlauf){
                errorMessages.add("Die Runde ist offen. Käufe und Unternehmenswerte liegen in der aktuellen Runde vor.");
            } else if (rundeEinstellung == rundeTransaktionen) {
               errorMessages.add("Die Runde ist offen. Käufe liegen in der aktuellen Runde vor.");
            } else if (rundeEinstellung == rundeAktienverlauf) {
                errorMessages.add("Die Runde ist offen. Unternehmenswerte liegen in der aktuellen Runde vor.");
            }

            if (errorMessages.isEmpty()){
                // Dividenden löschen
                SQL.table("UPDATE Transaktionen SET Dividende = 0 WHERE Runde = " + (rundeEinstellung - 1));
                // Kapitalverlauf löschen
                SQL.table("DELETE FROM Kapitalverlauf WHERE Runde = " + (rundeEinstellung - 1));
                // Spielstand löschen
                SQL.table("DELETE FROM Spielstand WHERE Runde = " + (rundeEinstellung - 1));
                // Runde verringern
                SQLEinstellungen.setEinstellung("RND", String.valueOf((rundeEinstellung - 1)));
            }
        }

        if(kauf_chb.isSelected() || wert_chb.isSelected()){
            // Runden ermitteln
            Integer rundeEinstellung = Start.getAktuelleRunde();
            Integer rundeTransaktionen = SQLSpiel.getOneInteger("SELECT Runde FROM Transaktionen WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen) LIMIT 1");
            Integer rundeAktienverlauf = SQLSpiel.getOneInteger("SELECT Runde FROM Aktienverlauf WHERE Runde = (SELECT MAX(Runde) FROM Aktienverlauf) LIMIT 1");

            Boolean openRunde = false;
            if(rundeEinstellung == rundeTransaktionen || rundeEinstellung == rundeAktienverlauf){
                openRunde = true;
            }

            // Runde offen
            if(openRunde) {
                // Kauf löschen, wenn Runden identisch
                if(kauf_chb.isSelected() && rundeEinstellung == rundeTransaktionen){
                    SQL.table("DELETE FROM Transaktionen WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen)");
                }
                // Wert löschen, wenn Runden identisch
                if(wert_chb.isSelected() && rundeEinstellung == rundeAktienverlauf){
                    SQL.table("DELETE FROM Aktienverlauf WHERE Runde = (SELECT MAX(Runde) FROM Aktienverlauf)");
                }
            // Runde geschlossen
            } else {
                // Kauf löschen
                if(kauf_chb.isSelected()){
                    SQL.table("DELETE FROM Kapitalverlauf WHERE Runde = " + (rundeEinstellung - 1));
                }
                // Werte und Dividenden löschen
                if(wert_chb.isSelected()){
                    SQL.table("DELETE FROM Aktienverlauf WHERE Runde = " + (rundeEinstellung - 1));
                    SQL.table("UPDATE Transaktionen SET Dividende = 0 WHERE Runde = " + (rundeEinstellung - 1));
                }
                // Kapitalverlauf löschen
                SQL.table("DELETE FROM Kapitalverlauf WHERE Runde = " + (rundeEinstellung - 1));
                // Spielstand löschen
                SQL.table("DELETE FROM Spielstand WHERE Runde = " + (rundeEinstellung - 1));
                // Runde verringern
                SQLEinstellungen.setEinstellung("RND", String.valueOf((rundeEinstellung - 1)));
            }
        }

        // Startkurs und Startkapital nur löschen, wenn keine Käufe und Werte vorhanden.
        if(startkurs_chb.isSelected() || startkapital_chb.isSelected()){
            if(SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Transaktionen") > 0){
                errorMessages.add("Es wurden noch nicht alle Käufe gelöscht.");
            }
            if(SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Aktienverlauf WHERE Runde > 0") > 0){
                errorMessages.add("Es wurden noch nicht alle Unternehmenswerte gelöscht.");
            }
            if(errorMessages.isEmpty()){
                if (startkurs_chb.isSelected()) {
                    // Startkurs löschen
                    SQL.table("DELETE FROM Aktienverlauf WHERE Runde = 0");
                }
                if(startkapital_chb.isSelected()) {
                    // Startkapital löschen
                    SQL.table("DELETE FROM Kapitalverlauf WHERE Runde = 0");
                }
            }
        }

        // Nur löschen, wenn Startkapital und/oder Startkurs erfolgreich gelöscht wurden
        if(errorMessages.isEmpty()) {
            if (person_chb.isSelected()) {
                // Personen löschen
                SQL.table("DELETE FROM Personen");
            }
            if (aktie_chb.isSelected()) {
                // Aktien löschen
                SQL.table("DELETE FROM Aktien");
            }
        }

        if(errorMessages.isEmpty()){
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich gelöscht.");
            backToStart();
        } else {
            Checks.showError(errorMessages);
        }

        // Serials zurücksetzen, wenn alle Daten gelöscht wurden
        if(SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Personen") == 0){
            // Der SQL liefert einen Wert, der entgegengenommen werden muss.
            SQLSpiel.getOneInteger("SELECT setval('personen_id_seq', 1, false)");
        }
        if(SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Aktienverlauf") == 0){
            SQLSpiel.getOneInteger("SELECT setval('aktienverlauf_id_seq', 1, false)");
        }
        if(SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Kapitalverlauf") == 0){
            SQLSpiel.getOneInteger("SELECT setval('kapitalverlauf_id_seq', 1, false)");
        }

        // Anzeige Runde aktualisieren
        Start.runde.setTextField(String.valueOf(Start.getAktuelleRunde()));
    }

    // Checkboxen demarkieren
    private static void checkboxUnselect(){
        rundeAll_chb.setSelected(false);
        rundeLast_chb.setSelected(false);
        rundeOpen_chb.setSelected(false);
        kauf_chb.setSelected(false);
        kauf_chb.setSelected(false);
        all_chb.setSelected(false);
        person_chb.setSelected(false);
        startkapital_chb.setSelected(false);
        aktie_chb.setSelected(false);
        startkurs_chb.setSelected(false);
    }

    // Zu Start zurückkehren
    private static void backToStart(){
        // Panel wechseln
        Cards.changeCard(Cards.nameStart);
        // Checkboxen beim Zurückkehren demarkieren
        checkboxUnselect();
    }
}