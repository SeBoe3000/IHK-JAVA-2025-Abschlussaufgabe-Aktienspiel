package Frontend.ActionListenerInsert;

import Backend.ElementTransaktionen;
import Datenbank.SQL;
import Datenbank.SQLSpiel;
import Datenbank.SQLTransaktionen;
import Frontend.ActionListenerUpdate.EinstellungenAktienListener;
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
    static Integer eingabeAnzahl = 0;

    @Override
    protected void checkFields() {
        Checks.checkField(Kauf.person, "isValidInteger", "Bitte eine gültige Zahl im Feld Person (ID) angeben.", errorMessages, errorFlags);
        Checks.checkField(Kauf.aktie, "isValidString", "Bitte eine gültige ISIN angeben.", errorMessages, errorFlags);
        Checks.checkFieldLenght(Kauf.aktie, 12,12,"isValidStringLaenge", "Die ISIN muss 12 Stellen lang sein.", errorMessages, errorFlags);
        Checks.checkField(Kauf.anzahl, "isValidInteger", "Bitte eine gültige Zahl im Feld Anzahl angeben.", errorMessages, errorFlags);
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
        // Prüfung eine Person darf nur eine gewisse Anzahl an Aktien kaufen
        if(anzahlAktienanzahlPerson() > EinstellungenTransaktionenListener.getEinstellungInteger("maxAktienPersonRunde")){
            errorMessages.add("Eine Person darf maximal " +
                    EinstellungenTransaktionenListener.getEinstellungInteger("maxAktienPersonRunde") +
                    " pro Runde gekauft werden. Mit dem aktuellen Kauf sind es aber " +
                    anzahlAktienanzahlPerson() + " Aktien. Bitte die Anzahl korrigieren.");
        }
        // Prüfung es kann nur eine gewisse Anzahl an Unternehmensaktien gekauft werden
        if(anzahlAktienanzahlUnternehmen() > EinstellungenAktienListener.getEinstellungInteger("maxAnzahlAktien")){
            errorMessages.add("Von einem Unternehmen dürfen maximal " +
                    EinstellungenAktienListener.getEinstellungInteger("maxAnzahlAktien") +
                    " Aktien gekauft werden. Mit dem aktuellen Kauf sind es aber " +
                    anzahlAktienanzahlPerson() + " Aktien. Bitte die Anzahl korrigieren.");
        }
        // Prüfung eine Person kann nur so viel Kaufen, bis das Startkapital aufgebraucht ist.
        if(aktienkauf(eingabePersonID) > startkapital(eingabePersonID)) {
            errorMessages.add("Die Person hat ein Startkapital von " + startkapital(eingabePersonID) +
                    " und mit dem aktuellen Kauf wären es " + aktienkauf(eingabePersonID) +
                    " . Bitte weniger kaufen.");
        }
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
        Integer runde = Start.getAktuelleRunde();
        // Summe Aktien aus Datenbank ermitteln
        int aktienDB = SQLSpiel.getOneInteger("SELECT COUNT(DISTINCT AktienISIN) FROM Transaktionen " +
                "WHERE Runde = " + runde);
        // Aktien, die nicht in Datenbank aber in bisherigen Liste sind dem Set dazuzählen
        for (ElementTransaktionen transaktion : TransaktionenList) {
            String aktieListe = transaktion.getAktie();
            int aktienListe = SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Transaktionen " +
                    "WHERE AktieISIN = " + "'" + aktieListe + "'" + " " +
                    "AND Runde = " + runde);
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
                    "WHERE AktieISIN = " + "'" + eingabeAktie + "'" + " " +
                    "AND Runde = " + runde);
            if (aktieEingabeDB == 0) {
                aktien.add(eingabeAktie); // Eingabeaktie hinzufügen
            }
        }
        // Summe Personen berechnen
        int anzahlAktien = aktienDB + aktien.size();
        // System.out.println("Anzahl Aktien: " + anzahlAktien);
        aktien.clear();
        return anzahlAktien;
    }

    protected Integer anzahlPersonen() {
        Set<String> personen = new HashSet<>();
        Integer runde = Start.getAktuelleRunde();
        // Summe Personen aus Datenbank ermitteln
        int personenDB = SQLSpiel.getOneInteger("SELECT COUNT(DISTINCT PersonID) FROM Transaktionen " +
                "WHERE Runde = " + runde);
        // Personen, die nicht in Datenbank aber in bisherigen Liste sind dem Set dazuzählen
        for (ElementTransaktionen transaktion : TransaktionenList) {
            int personListe = transaktion.getPerson();
            int personenListe = SQLSpiel.getOneInteger("SELECT COUNT(*) FROM Transaktionen " +
                    "WHERE PersonID = " + personListe + " " +
                    "AND Runde = " + runde);
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
                    "WHERE PersonID = " + eingabePersonID + " " +
                    "AND Runde = " + runde );
            if (personEingabeDB == 0) {
                personen.add(String.valueOf(eingabePersonID)); // Eingabeperson hinzufügen
            }
        }
        // Summe Personen berechnen
        int anzahlPersonen = personenDB + personen.size();
        // System.out.println("Anzahl Personen: " + anzahlPersonen);
        personen.clear();
        return anzahlPersonen;
    }

    protected Integer anzahlAktienanzahlPerson() {
        Integer runde = Start.getAktuelleRunde();
        // Summe Aktienanzahl aus Datenbank ermitteln
        int aktienanzahlDB = SQLSpiel.getOneInteger("SELECT COUNT(DISTINCT Aktienanzahl) FROM Transaktionen " +
                "WHERE Runde = " + runde + " " +
                "AND PersonID = " + eingabePersonID);
        // Aktienanzahl aus Liste dazuzählen
        int aktienanzahlListe = 0;
        for (ElementTransaktionen transaktion : TransaktionenList) {
            if (transaktion.getPerson() == eingabePersonID) {
                aktienanzahlListe += transaktion.getAnzahl();
            }
        }
        // Summe Aktienanzahl berechnen (mit Eingabe)
        int aktienanzahl = aktienanzahlDB + aktienanzahlListe + eingabeAnzahl;
        // System.out.println("Anzahl Aktienanzahl Person: " + aktienanzahl);
        return aktienanzahl;
    }

    protected Integer anzahlAktienanzahlUnternehmen() {
        Integer runde = Start.getAktuelleRunde();
        // Summe Aktienanzahl aus Datenbank ermitteln
        int aktienanzahlDB = SQLSpiel.getOneInteger("SELECT COUNT(DISTINCT Aktienanzahl) FROM Transaktionen " +
                "WHERE Runde = " + runde + " " +
                "AND AktieISIN = " + "'" + eingabeAktie + "'");
        // Aktienanzahl aus Liste dazuzählen
        int aktienanzahlListe = 0;
        for (ElementTransaktionen transaktion : TransaktionenList) {
            if (transaktion.getAktie() == eingabeAktie) {
                aktienanzahlListe += transaktion.getAnzahl();
            }
        }
        // Summe Aktienanzahl berechnen (mit Eingabe)
        int aktienanzahl = aktienanzahlDB + aktienanzahlListe + eingabeAnzahl;
        // System.out.println("Anzahl Aktienanzahl Unternehmen: " + aktienanzahl);
        return aktienanzahl;
    }

    public static Float aktienkauf(Integer eingabePersonID){
        Integer runde = Start.getAktuelleRunde();
        // Wert Datenbank
        float aktienkaufDatenbank = Start.aktienwertBerechnen(eingabePersonID, 1);
        // Wert Liste
        float aktienkaufListe = 0F;
        for (ElementTransaktionen transaktion : TransaktionenList) {
            if (transaktion.getPerson() == eingabePersonID) {
                Integer aktienanzahlListe = transaktion.getAnzahl();
                String aktie = transaktion.getAktie();
                Float aktienwertListe = SQLSpiel.getOneFloat("SELECT Aktienkurs " +
                                "FROM Aktienverlauf " +
                                "WHERE Runde = " + (runde - 1) + " " +
                                "AND AktieISIN = " + "'" + eingabeAktie + "'");
                aktienkaufListe += (aktienanzahlListe * aktienwertListe);
            }
        }
        // Wert Eingabe
        float aktienkaufEingabe = eingabeAnzahl * SQLSpiel.getOneFloat("SELECT Aktienkurs " +
                "FROM Aktienverlauf " +
                "WHERE Runde = " + (runde - 1) + " " +
                "AND AktieISIN = " + "'" + eingabeAktie + "'");
        // Summe
        float aktienkaufSumme = aktienkaufDatenbank + aktienkaufListe + aktienkaufEingabe;
        return aktienkaufSumme;
    }

    public static Float startkapital(Integer eingabePersonID){
        float startkapital = SQLSpiel.getOneFloat("SELECT Kapital FROM Kapitalverlauf " +
                "WHERE Runde = " + (Start.getAktuelleRunde() - 1) + " " +
                "AND PersonID = " + eingabePersonID);
        return startkapital;
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
        Checks.clearOneField(Kauf.restwert);
    }

    @Override
    protected void changePanel() {
        Cards.changeCard(Cards.nameStart);
    }
}