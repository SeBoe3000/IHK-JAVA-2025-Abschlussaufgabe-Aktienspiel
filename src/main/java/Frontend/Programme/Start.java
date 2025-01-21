package Frontend.Programme;

import Backend.ElementKapitalverlauf;
import Datenbank.SQL;
import Datenbank.SQLKapitalverlauf;
import Datenbank.SQLSpiel;
import Frontend.ActionListenerUpdate.EinstellungenTransaktionenListener;
import Frontend.Cards;
import Frontend.Checks.Checks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Start extends JPanel {
    // Zum Zählen der Runden
    public static Integer runde = 1;

    // Zum Speichern der Fehlermeldungen
    ArrayList<String> errorMessages = new ArrayList<>();

    JButton create_importExample = new JButton("Beispieldaten importieren");
    JButton open_stammdaten = new JButton("Stammdaten erfassen");
    JButton open_einstellung = new JButton("Einstellungen");
    ButtonGroup group_stammdaten = new ButtonGroup();

    JLabel bewegungsdaten = new JLabel("Bewegungsdaten");
    JButton create_kauf = new JButton("Käufe erfassen");
    JButton create_wert = new JButton("Unternehmenswerte erfassen");

    ButtonGroup group_bewegungsdaten = new ButtonGroup();
    JButton create_runde = new JButton("Nächste Runde");

    JLabel anzeige = new JLabel("Anzeige");
    JButton show_spielstand = new JButton("Spielstand anzeigen");

    public Start(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Stammdaten hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        group_stammdaten.add(create_importExample);
        group_stammdaten.add(open_stammdaten);
        group_stammdaten.add(open_einstellung);

        JPanel group_stammdaten = new JPanel();
        group_stammdaten.setLayout(new BoxLayout(group_stammdaten, BoxLayout.X_AXIS));
        group_stammdaten.add(create_importExample);
        group_stammdaten.add(open_stammdaten);
        group_stammdaten.add(open_einstellung);
        add(group_stammdaten, gbc);

        // Label Bewegungsdaten hinzufügen
        gbc.gridy = 1; // Zeile
        add(bewegungsdaten, gbc);

        // Bewegungsdaten (Kauf und Wert) hinzufügen
        gbc.gridy = 2; // Zeile

        group_bewegungsdaten.add(create_kauf);
        group_bewegungsdaten.add(create_wert);

        JPanel group_bewegungsdaten = new JPanel();
        group_bewegungsdaten.setLayout(new BoxLayout(group_bewegungsdaten, BoxLayout.X_AXIS));
        group_bewegungsdaten.add(create_kauf);
        group_bewegungsdaten.add(create_wert);
        add(group_bewegungsdaten, gbc);

        // Nächste Runde hinzufügen
        gbc.gridy = 3; // Zeile
        add(create_runde, gbc);

        // Label Anzeige hinzufügen
        gbc.gridy = 4; // Zeile
        add(anzeige, gbc);

        // Spielstand anzeigen hinzufügen
        gbc.gridy = 5; // Zeile
        add(show_spielstand, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {

        ActionListener importExample = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameBeispieldatenImportieren);
            }
        };
        create_importExample.addActionListener(importExample);

        ActionListener stammdaten = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameStammdaten);
            }
        };
        open_stammdaten.addActionListener(stammdaten);

        ActionListener einstellung = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameEinstellungen);
            }
        };
        open_einstellung.addActionListener(einstellung);

        ActionListener kauf = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameKauf);
            }
        };
        create_kauf.addActionListener(kauf);

        ActionListener wert = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameWert);
            }
        };
        create_wert.addActionListener(wert);

        ActionListener runde = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ermittlung der aktuellen Runden
                Integer existsStartkapital = SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Kapitalverlauf");
                Integer existsStartkurs = SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Aktienverlauf");
                Integer rundeTransaktionen = SQLSpiel.getOneInteger("SELECT Runde FROM Transaktionen " +
                        "ORDER BY Runde DESC LIMIT 1");
                Integer rundeAktienverlauf = SQLSpiel.getOneInteger("SELECT Runde FROM Aktienverlauf " +
                        "ORDER BY Runde DESC LIMIT 1");

                System.out.println("Runde Transaktionen: " + rundeTransaktionen + " Aktienverlauf: " + rundeAktienverlauf);

                if (existsStartkapital == 0 || existsStartkurs == 0){
                    if(existsStartkapital == 0) {
                        errorMessages.add("Bitte Startkapital erfassen.");
                    }
                    if(existsStartkurs == 0) {
                        errorMessages.add("Bitte Startkurse erfassen.");
                    }
                } else if (rundeTransaktionen != rundeAktienverlauf || rundeTransaktionen == 0 || rundeAktienverlauf == 0) {
                    if (rundeTransaktionen > rundeAktienverlauf || rundeAktienverlauf == 0) {
                        errorMessages.add("Bitte Unternehmenswerte (Aktienkurs und Kassenbestand) erfassen.");
                    }
                    if (rundeTransaktionen < rundeAktienverlauf || rundeTransaktionen == 0) {
                        errorMessages.add("Bitte Käufe erfassen.");
                    }
                } else if (rundeTransaktionen == rundeAktienverlauf){
                    Integer anzahlPersonen = SQLSpiel.getOneInteger("SELECT COUNT(DISTINCT personid) " +
                            "FROM Transaktionen WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen)");
                    Integer anzahlAktien = SQLSpiel.getOneInteger("SELECT COUNT(DISTINCT aktieisin) " +
                            "FROM Transaktionen WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen)");

                    System.out.println("Anzahl Personen: " + anzahlPersonen + " Aktien: " + anzahlAktien);

                    // Einstellungen holen
                    Integer minPersonRunde = EinstellungenTransaktionenListener.getEinstellungInteger("minPersonRunde");
                    Integer maxPersonRunde = EinstellungenTransaktionenListener.getEinstellungInteger("maxPersonRunde");
                    Integer minAktieRunde = EinstellungenTransaktionenListener.getEinstellungInteger("minAktieRunde");
                    Integer maxAktieRunde = EinstellungenTransaktionenListener.getEinstellungInteger("maxAktieRunde");

                    if(anzahlPersonen < minPersonRunde || anzahlPersonen > maxPersonRunde){
                        errorMessages.add("Es müssen mindestens " + minPersonRunde + " und maximal " + maxPersonRunde +
                                " Personen pro Runde sein. Aktuell sind es aber " + anzahlPersonen + " Personen. " +
                                "Bitte die Anzahl korrigieren.");
                    }
                    if(anzahlAktien < minAktieRunde || anzahlAktien > maxAktieRunde){
                        errorMessages.add("Es müssen mindestens " + minAktieRunde + " und maximal " + maxAktieRunde +
                                " Aktien pro Runde sein. Aktuell sind es aber " + anzahlAktien + " Aktien. Bitte die Anzahl korrigieren.");
                    }

                    if(errorMessages.isEmpty()){
                        // Prüfung, zu jeder gekauften Aktie liegt ein Aktienkurs und Kassenbestand vor
                        // TODO: Prüfen, auch ein Kassenbestand von 0 ist gültig.
                        Integer anzahlAktienOhneWertRunde = SQLSpiel.getOneInteger("SELECT " +
                                "COUNT(CASE WHEN aktienverlauf.kassenbestand IS NULL THEN 1 END) " +
                                "FROM Transaktionen LEFT JOIN Aktienverlauf ON " +
                                "Transaktionen.Runde = Aktienverlauf.Runde AND " +
                                "Transaktionen.Aktieisin = Aktienverlauf.Aktieisin " +
                                "WHERE Transaktionen.Runde = (SELECT MAX(Transaktionen.Runde) FROM Transaktionen)");

                        System.out.println("Anzahl Aktien ohne Wert: " + anzahlAktienOhneWertRunde);

                        if(anzahlAktienOhneWertRunde > 0) {
                            String aktienOhneWertRunde = SQLSpiel.getAktienOhneWert();
                            // System.out.println("Aufzählung Aktien ohne Wert: " + aktienOhneWertRunde);
                            errorMessages.add("Zu den Aktien " + aktienOhneWertRunde + " müssen zu der aktuellen Runde noch Werte erfasst werden.");
                        }
                    }

                    // Berechnung der Dividende
                    if(errorMessages.isEmpty()){
                        // Aktien abspeichern
                        ArrayList<String> AktienRunde =  SQLSpiel.getArrayListeString("SELECT Aktieisin " +
                                "FROM Transaktionen " +
                                "WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen) " +
                                "GROUP BY Aktieisin ORDER BY Aktieisin ASC");

                        // Einstellungen holen
                        Float firstDividende = EinstellungenTransaktionenListener.getEinstellungFloat("firstDividende");
                        Float secondDividende = EinstellungenTransaktionenListener.getEinstellungFloat("secondDividende");

                        // Für jede Aktie die Berechnung durchgehen
                        for (String aktieisin : AktienRunde) {
                            Integer anzahlPersonenErsterPlatz = SQLSpiel.getOneInteger("SELECT COUNT(*) " +
                                    "FROM Transaktionen " +
                                    "WHERE Aktieisin = " + "'" + aktieisin + "'" + " " +
                                    "AND Runde = (SELECT MAX(Runde) FROM Transaktionen) " +
                                    "GROUP BY Aktienanzahl " +
                                    "ORDER BY Aktienanzahl DESC " +
                                    "LIMIT 1");
                            Integer anzahlPersonenZweiterPlatz = SQLSpiel.getOneInteger("SELECT COUNT(*) " +
                                    "FROM Transaktionen " +
                                    "WHERE Aktieisin = " + "'" + aktieisin + "'" + " AND Aktienanzahl < (" +
                                    "SELECT MAX(Aktienanzahl) " +
                                    "FROM Transaktionen " +
                                    "WHERE Aktieisin = " + "'" + aktieisin + "'" + ") " +
                                    "AND Runde = (SELECT MAX(Runde) FROM Transaktionen) " +
                                    "GROUP BY Aktienanzahl " +
                                    "ORDER BY Aktienanzahl DESC " +
                                    "LIMIT 1");
                            // Die beiden nachfolgenden SQL's unterscheiden sich nur die die zu selektierende Spalte
                            Integer aktienkursErsterPlatz = SQLSpiel.getOneInteger("SELECT Aktienanzahl " +
                                    "FROM Transaktionen " +
                                    "WHERE Aktieisin = " + "'" + aktieisin + "'" + " " +
                                    "AND Runde = (SELECT MAX(Runde) FROM Transaktionen) " +
                                    "GROUP BY Aktienanzahl " +
                                    "ORDER BY Aktienanzahl DESC " +
                                    "LIMIT 1");
                            Integer aktienkursZweiterPlatz = SQLSpiel.getOneInteger("SELECT Aktienanzahl " +
                                    "FROM Transaktionen " +
                                    "WHERE Aktieisin = " + "'" + aktieisin + "'" + " AND Aktienanzahl < (" +
                                    "SELECT MAX(Aktienanzahl) " +
                                    "FROM Transaktionen " +
                                    "WHERE Aktieisin = " + "'" + aktieisin + "'" + ") " +
                                    "AND Runde = (SELECT MAX(Runde) FROM Transaktionen) " +
                                    "GROUP BY Aktienanzahl " +
                                    "ORDER BY Aktienanzahl DESC " +
                                    "LIMIT 1");

                            // Kassenbestand holen
                            Float kassenbestandAktie = SQLSpiel.getOneFloat("SELECT Kassenbestand " +
                                    "FROM Aktienverlauf " +
                                    "WHERE Runde = (SELECT MAX(Runde) FROM Aktienverlauf) " +
                                    "AND Aktieisin = " + "'" + aktieisin + "'");

                            // Abspeichern der Personen mit dem ersten Platz
                            ArrayList<Integer> PersonenAktieErsterPlatz = SQLSpiel.getArrayListeInteger("SELECT personid " +
                                    "FROM Transaktionen " +
                                    "WHERE Aktienanzahl = " + "'" + aktienkursErsterPlatz + "'" + " " +
                                    "AND Aktieisin = " + "'" + aktieisin + "'" + " " +
                                    "AND Runde = (SELECT MAX(Runde) FROM Transaktionen)");

                            // Abspeichern der Personen mit dem zweiten Platz
                            ArrayList<Integer> PersonenAktieZweiterPlatz = SQLSpiel.getArrayListeInteger("SELECT personid " +
                                        "FROM Transaktionen " +
                                        "WHERE Aktienanzahl = " + "'" + aktienkursZweiterPlatz + "'" + " " +
                                        "AND Aktieisin = " + "'" + aktieisin + "'" + " " +
                                        "AND Runde = (SELECT MAX(Runde) FROM Transaktionen)");

                            // Abspeichern der restlichen Personen
                            ArrayList<Integer> PersonenAktieRestlicherPlatz = SQLSpiel.getArrayListeInteger("SELECT personid " +
                                    "FROM Transaktionen " +
                                    "WHERE Aktienanzahl <> " + "'" + aktienkursZweiterPlatz + "'" + " " +
                                    "AND Aktienanzahl <> " + "'" + aktienkursErsterPlatz + "'" + " " +
                                    "AND Aktieisin = " + "'" + aktieisin + "'" + " " +
                                    "AND Runde = (SELECT MAX(Runde) FROM Transaktionen)");

                            System.out.println("Aktien aus Arrayliste: " + aktieisin + "   " +
                                    " Erster Platz Personen: " + anzahlPersonenErsterPlatz + " Wert: " + aktienkursErsterPlatz + "   " +
                                    " Zweiter Platz Personen: " + anzahlPersonenZweiterPlatz + " Wert: " + aktienkursZweiterPlatz + "   " +
                                    " Kassenbestand: " + kassenbestandAktie +
                                    " Dividende erste: " + firstDividende + " zweite: " + secondDividende);

                            Float dividendeGenerell = 0F; // 10% vom Aktienwert
                            Float dividendeSonderErster = 0F;
                            Float dividendeSonderZweiter = 0F;

                            // Berechnung der Sonderdividende, aber nur wenn der Kassenbestand größer 0 ist
                            if(kassenbestandAktie > 0) {
                                // Prüfung: wie oft kommt die Aktienanzahl vom ersten und zweiten Platz vor?
                                if (anzahlPersonenErsterPlatz > 1) {
                                    dividendeSonderErster = Float.valueOf(kassenbestandAktie / 100 * (firstDividende + secondDividende) / anzahlPersonenErsterPlatz);
                                } else if (anzahlPersonenErsterPlatz == 1) {
                                    // Gibt es keinen zweiten Platz, bekommt der Erste alles (Dividende erster und zweiter)
                                    if (anzahlPersonenZweiterPlatz == 0) {
                                        dividendeSonderErster = Float.valueOf(kassenbestandAktie / 100 * (firstDividende + secondDividende));
                                        // Meldung ausgeben, dass es nur einen ersten Platz gab.
                                        errorMessages.add("Achtung: zu der Aktie " + aktieisin + " gab es nur einen ersten Platz.");
                                    } else {
                                        dividendeSonderErster = Float.valueOf(kassenbestandAktie / 100 * firstDividende);
                                        dividendeSonderZweiter = Float.valueOf(kassenbestandAktie / 100 * (secondDividende) / anzahlPersonenZweiterPlatz);
                                    }
                                }
                                System.out.println("Kassenbestand: " + kassenbestandAktie +
                                        " Prozentzahl 1: " + firstDividende + " Prozentzahl 2: " + secondDividende +
                                        " Anzahl Personen 1: " + anzahlPersonenErsterPlatz +
                                        " Anzahl Personen 2: " + anzahlPersonenZweiterPlatz +
                                        " Sonderdividende 1: " + dividendeSonderErster +
                                        " Sonderdividende 2: " + dividendeSonderZweiter);
                            }

                            // Dividenden updaten
                            for (Integer personid : PersonenAktieErsterPlatz) {
                                // TODO: generelle Dividende variabel machen
                                dividendeGenerell = aktienwertBerechnen(personid) / 100 * 10; // generelle Dividende berechnen
                                updateDividende(personid, aktieisin, dividendeSonderErster + dividendeGenerell);
                                /*
                                SQL.table("UPDATE Transaktionen " +
                                        "SET Dividende = " + (dividendeSonderErster + dividendeGenerell) + " " +
                                        "WHERE Personid = " + personid + " " +
                                        "AND Aktieisin = " + "'" + aktieisin + "'" + " " +
                                        "AND Runde = (SELECT MAX(Runde) FROM Transaktionen) ");

                                 */
                            }
                            PersonenAktieErsterPlatz.clear();

                            for (Integer personid : PersonenAktieZweiterPlatz) {
                                dividendeGenerell = aktienwertBerechnen(personid) / 100 * 10; // generelle Dividende berechnen
                                updateDividende(personid, aktieisin, dividendeSonderZweiter + dividendeGenerell);
                                /*SQL.table("UPDATE Transaktionen " +
                                        "SET Dividende = " + (dividendeSonderZweiter + dividendeGenerell) + " " +
                                        "WHERE Personid = " + personid + " " +
                                        "AND Aktieisin = " + "'" + aktieisin + "'" + " " +
                                        "AND Runde = (SELECT MAX(Runde) FROM Transaktionen) ");

                                 */
                            }
                            PersonenAktieZweiterPlatz.clear();

                            for (Integer personid : PersonenAktieRestlicherPlatz) {
                                dividendeGenerell = aktienwertBerechnen(personid) / 100 * 10; // generelle Dividende berechnen
                                updateDividende(personid, aktieisin, dividendeGenerell);
                                /*SQL.table("UPDATE Transaktionen " +
                                        "SET Dividende = " + (dividendeGenerell) + " " +
                                        "WHERE Personid = " + personid + " " +
                                        "AND Aktieisin = " + "'" + aktieisin + "'" + " " +
                                        "AND Runde = (SELECT MAX(Runde) FROM Transaktionen) ");
                                 */
                            }
                            PersonenAktieRestlicherPlatz.clear();
                        }

                        // Anlegen Datensatz in Tabelle Kapitalverlauf mit dem aktuellen Spielstand
                        updateKapital();

                        // TODO: Runde in Datenbank speichern, ansonsten wird beim erneuten Aufruf wieder von vorne begonnen.
                        // TODO: dann aber auch eine Funktion zum erneuten Spielen einbinden und die alten Daten löschen.
                        // TODO: prüfen, ob Runde in Datenbank gespeichert werden muss, oder ob diese aus Transaktionen / Aktienverlauf ermittelt werden sollte. Dafür Programm zwischenzeitlich schließen.
                        Start.runde++;
                        System.out.println("Runde: " + Start.runde);
                        JOptionPane.showMessageDialog(null, "Runde wurde erfolgreich gespielt.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);

                        // TODO: Abfragen für Spielstand ggf. aktualisieren


                    }
                }
                Checks.showError(errorMessages); // Ausgabe Fehlermeldung(en)
            }
        };
        create_runde.addActionListener(runde);

        ActionListener spielstand = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameSpielstand);
            }
        };
        show_spielstand.addActionListener(spielstand);
    }

    private static void updateDividende(Integer personid, String aktieisin, Float dividende) {
        SQL.table("UPDATE Transaktionen " +
                "SET Dividende = " + dividende + " " +
                "WHERE Personid = " + personid + " " +
                "AND Aktieisin = " + "'" + aktieisin + "'" + " " +
                "AND Runde = (SELECT MAX(Runde) FROM Transaktionen) ");
    }

    private static void updateKapital(){
        // Abspeichern vom Kapitalverlauf
        ArrayList<ElementKapitalverlauf> KapitalverlaufList = new ArrayList<>();

        // Abspeichern der Personen aus der aktuellen Runde
        ArrayList<Integer> PersonenRunde = SQLSpiel.getArrayListeInteger("SELECT personid " +
                "FROM Transaktionen " +
                "WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen) " +
                "GROUP BY personid");
        // Für jede Person wird das gesamte Vermögen berechnet aus der Summe der Dividenden und Aktienwerte (Kurs * Anzahl)
        for (Integer personid: PersonenRunde) {
            Float dividende = SQLSpiel.getOneFloat("SELECT SUM(Dividende) " +
                    "FROM Transaktionen " +
                    "WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen) " +
                    "AND Personid = " + "'" + personid + "'" + " " +
                    "GROUP BY Personid");
            Float aktienwert = aktienwertBerechnen(personid);

            // Um Exceptions zu verhindern
            if (dividende == null) dividende = 0f;
            if (aktienwert == null) aktienwert = 0f;

            Float summe = dividende + aktienwert;

            // Elemente der Liste hinzufügen
            ElementKapitalverlauf kapitalverlauf = new ElementKapitalverlauf(runde, personid, summe);
            KapitalverlaufList.add(kapitalverlauf);
        }

        // Insert durchführen
        SQLKapitalverlauf.selectInsertTableKapitalverlauf(KapitalverlaufList);
        PersonenRunde.clear();
    }

    public static Float aktienwertBerechnen(Integer personid){
        Float aktienwert = SQLSpiel.getOneFloat("SELECT " +
                "SUM((Transaktionen.Aktienanzahl * Aktienverlauf.Aktienkurs)) " +
                "FROM Transaktionen JOIN Aktienverlauf " +
                "ON Transaktionen.Aktieisin = Aktienverlauf.Aktieisin " +
                "AND Transaktionen.Runde = Aktienverlauf.Runde " +
                "WHERE Transaktionen.Runde = (SELECT MAX(Transaktionen.Runde) FROM Transaktionen) " +
                "AND Transaktionen.Personid = " + "'" + personid + "'");
        return aktienwert;
    }
}