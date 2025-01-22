package Frontend.ActionListenerInsert;

import Backend.ElementTransaktionen;
import Datenbank.SQL;
import Datenbank.SQLSpiel;
import Datenbank.SQLTransaktionen;
import Frontend.ActionListenerUpdate.EinstellungenTransaktionenListener;
import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Programme.Bewegungsdaten.Kauf;
import Frontend.Programme.Start;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KaufListener extends MyActionListenerInsert {
    public KaufListener(JButton Btn) {
        super(Btn);
    }

    // Zum Speichern der Ergebnisse
    public static ArrayList<ElementTransaktionen> TransaktionenList = new ArrayList<>();

    // Zu füllende Felder
    static Integer eingabePersonID = 0;
    static String eingabeAktie = "";
    Integer eingabeAnzahl = 0;

    @Override
    protected void checkFields() {
        Checks.checkField(Kauf.person, "isValidInteger", "Bitte eine gültige Zahl im Feld Person (ID) angeben.", errorMessages);
        Checks.checkField(Kauf.aktie, "isValidString", "Bitte eine gültige ISIN angeben.", errorMessages);
        Checks.checkFieldLenght(Kauf.aktie, 12,12,"isValidStringLaenge", "Die ISIN muss 12 Stellen lang sein.", errorMessages);
        Checks.checkField(Kauf.anzahl, "isValidInteger", "Bitte eine gültige Zahl im Feld Anzahl angeben.", errorMessages);
    }

    @Override
    protected void fillFields() {
        eingabePersonID = Integer.valueOf(Kauf.person.getTextfield());
        eingabeAktie = Kauf.aktie.getTextfield();
        eingabeAnzahl = Integer.valueOf(Kauf.anzahl.getTextfield());
    }

    @Override
    protected void nextChecks() {
        // PersonID muss in Tabelle Personen vorhanden sein.
        if(!SQL.checkElementAlreadyInDatenbankOneInteger(eingabePersonID, "id","Personen")){
            errorMessages.add("Die ausgewählte Person ist nicht in der Tabelle Personen vorhanden.");
        }
        // Aktie muss in Tabelle Aktien vorhanden sein.
        if(!SQL.checkElementAlreadyInDatenbankOneString(eingabeAktie, "isin","Aktien")){
            errorMessages.add("Die ausgewählte Aktie ist nicht in der Tabelle Aktien vorhanden.");
        }
        // Prüfung, ob Element in der Liste vorhanden ist.
        if(checkElementAlreadyInList(eingabePersonID, eingabeAktie)){
            // TODO: ggf. den Kauf erhöhen
            errorMessages.add("Das Element befindet sich bereits in der ElementListe. Bitte einen anderen Datensatz angeben.");
        }
        // Prüfung, ob Element bereits in Datenbank vorhanden ist
        if(SQL.checkElementAlreadyInDatenbankIntegerIntegerString(Start.getAktuelleRunde(), eingabePersonID, eingabeAktie, "runde", "PersonID", "AktieISIN", "Transaktionen")){
            // TODO: ggf. den Kauf erhöhen
            errorMessages.add("Das Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }
        // Prüfung nicht zu viele Personen verwendet (Mindestanzahl wird beim Spielen der nächsten Runde geprüft)
        if(anzahlPersonen() > EinstellungenTransaktionenListener.getEinstellungInteger("maxPersonRunde")){
            errorMessages.add("Es dürfen maximal " +
                    EinstellungenTransaktionenListener.getEinstellungInteger("maxPersonRunde") +
                    " Personen an der Runde teilnehmen.");
        }
        // Prüfung nicht zu viele Aktien verwendet (Mindestanzahl wird beim Spielen der nächsten Runde geprüft)
        if(anzahlAktien() > EinstellungenTransaktionenListener.getEinstellungInteger("maxAktieRunde")){
            errorMessages.add("Es dürfen maximal " +
                    EinstellungenTransaktionenListener.getEinstellungInteger("maxPersonRunde") +
                    " Aktien an der Runde teilnehmen.");
        }


        Integer anzahlAktienDB = 0;
        Integer anzahlAktienListe = 0;

        Integer startkapital = 0;
        Integer kaufDB = 0;
        Integer kaufListe = 0;



        Integer anzahlAktienUnternehmenDB = 0;
        Integer anzahlAktienUnternehmenListe = 0;

        // TODO: Prüfung eine Person hat maximal 30 Anteile erworben (Liste + Datenbank)

        // TODO: Prüfung eine Person hat nicht mehr investiert, die das Kapital aus der Runde davor hergegeben hat (Liste + Datenbank)
        // TODO: hier auch Prüfung Startkapital ist vorhanden.

        // TODO: Prüfung, noch Aktien vom Unternehmen vorhanden (Liste + Datenbank) - Maximale Anzahl Aktien pro Unternehmen

    }

    // Prüfung, ob der Wert bereits in der Liste vorhanden ist
    public static boolean checkElementAlreadyInList(Integer person, String aktie){
        boolean inList = false;
        for(ElementTransaktionen Transaktion: TransaktionenList){
            if (person.equals(Transaktion.getPerson()) && aktie.equals(Transaktion.getAktie())){
                // System.out.println("Bereits vorhanden");
                inList = true;
            }
        }
        return inList;
    }

    protected Integer anzahlAktien() {
        Set<String> aktien = new HashSet<>();
        // Summe Aktien aus Datenbank ermitteln
        int aktienDB = SQLSpiel.getOneInteger("SELECT COUNT(DISTINCT AktienISIN) FROM Transaktionen");

        // Aktien, die nicht in Datenbank aber in bisherigen Liste sind dem Set dazuzählen
        for (ElementTransaktionen transaktion : TransaktionenList) {
            String aktieListe = transaktion.getAktie();
            int aktienListe = SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Transaktionen " +
                    "WHERE AktieISIN = " + "'" + aktieListe + "'");
            if (aktienListe == 0) {
                aktien.add(aktieListe);
            }
        }

        // Eingabe aus aktuellen Programm dazuzählen, wenn nicht in Datenbank oder Liste vorhanden
        boolean aktieNotInListe = true;
        for (ElementTransaktionen transaktion : TransaktionenList) {
            if (transaktion.getAktie() == eingabeAktie) {
                aktieNotInListe = false;
                break;
            }
        }
        if (aktieNotInListe) {
            int aktieEingabeDB = SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Transaktionen " +
                    "WHERE AktieISIN = " + "'" + eingabeAktie + "'");
            if (aktieEingabeDB == 0) {
                aktien.add(eingabeAktie); // Eingabeaktie hinzufügen
            }
        }

        // Summe Personen berechnen
        int anzahlAktien = aktienDB + aktien.size();
        System.out.println("Anzahl Aktien: " + anzahlAktien);
        aktien.clear();

        return anzahlAktien;
    }

    protected Integer anzahlPersonen() {
        Set<String> personen = new HashSet<>();
        // Summe Personen aus Datenbank ermitteln
        int personenDB = SQLSpiel.getOneInteger("SELECT COUNT(DISTINCT PersonID) FROM Transaktionen");

        // Personen, die nicht in Datenbank aber in bisherigen Liste sind dem Set dazuzählen
        for (ElementTransaktionen transaktion : TransaktionenList) {
            int personListe = transaktion.getPerson();
            int personenListe = SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Transaktionen " +
                    "WHERE PersonID = " + "'" + personListe + "'");
            if (personenListe == 0) {
                personen.add(String.valueOf(personListe));
            }
        }

        // Eingabe aus aktuellen Programm dazuzählen, wenn nicht in Datenbank oder Liste vorhanden
        boolean personNotInListe = true;
        for (ElementTransaktionen transaktion : TransaktionenList) {
            if (transaktion.getPerson() == eingabePersonID) {
                personNotInListe = false;
                break;
            }
        }
        if (personNotInListe) {
            int personEingabeDB = SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Transaktionen " +
                    "WHERE PersonID = " + "'" + eingabePersonID + "'");
            if (personEingabeDB == 0) {
                personen.add(String.valueOf(eingabePersonID)); // Eingabeperson hinzufügen
            }
        }

        // Summe Personen berechnen
        int anzahlPersonen = personenDB + personen.size();
        System.out.println("Anzahl Personen: " + anzahlPersonen);
        personen.clear();

        return anzahlPersonen;
    }

    @Override
    protected void elementInList() {
        // Element der Liste hinzufügen
        ElementTransaktionen transaktionen = new ElementTransaktionen(Start.getAktuelleRunde(), eingabePersonID, eingabeAktie, eingabeAnzahl, 0F);
        TransaktionenList.add(transaktionen);
        // Nach Hinzufügen die Felder leeren
        felderLeeren();
    }

    @Override
    protected boolean checkFieldsfilled() {
        Boolean filled = true;
        if(!Checks.checkOneFieldfilled(Kauf.person) && !Checks.checkOneFieldfilled(Kauf.aktie) && !Checks.checkOneFieldfilled(Kauf.anzahl)) {
            filled = false;
        }
        // System.out.println("filled: " +  filled);
        return filled;
    }

    @Override
    protected boolean checkElementInList() {
        Boolean action = Checks.checkElementInList(TransaktionenList);
        return action;
    }

    @Override
    protected void elementInsert() {
        // Option 1: Liste Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert ausgeben.
        // Kein Eingriff bei in der Zwischenzeit geänderten Daten.
        if(SQLTransaktionen.selectInsertTableTransaktionen(TransaktionenList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Diese wurden nicht erfasst. Der Rest wurde verarbeitet.");
        }
        // TODO: Option 2 prüfen, siehe AktienListener
    }

    @Override
    protected void clearliste() {
        TransaktionenList.clear();
    }

    @Override
    protected void felderLeeren(){
        Checks.clearOneField(Kauf.person);
        Checks.clearOneField(Kauf.aktie);
        Checks.clearOneField(Kauf.anzahl);
    }

    @Override
    protected void changePanel() {
        Cards.changeCard(Cards.nameStart);
    }
}
