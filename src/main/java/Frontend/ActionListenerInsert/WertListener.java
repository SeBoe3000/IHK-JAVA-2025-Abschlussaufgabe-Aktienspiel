package Frontend.ActionListenerInsert;

import Backend.ElementAktienverlauf;
import Datenbank.SQL;
import Datenbank.SQLAktienverlauf;
import Datenbank.SQLSpiel;
import Frontend.ActionListenerUpdate.EinstellungenAktienListener;
import Frontend.ActionListenerUpdate.EinstellungenAktienverlaufListener;
import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Programme.Bewegungsdaten.Wert;
import Frontend.Programme.Start;

import javax.swing.*;
import java.util.ArrayList;

public class WertListener extends MyActionListenerInsert {
    public WertListener(JButton Btn) {
        super(Btn);
    }

    // Zum Speichern der Ergebnisse
    public static ArrayList<ElementAktienverlauf> WertList = new ArrayList<>();

    // Zu füllende Felder
    String eingabeAktieIsin = "";
    Float eingabeKurs = 0F;
    Float eingabeKassenbestand = 0F;

    @Override
    protected void checkFields() {
        Checks.checkField(Wert.aktie, "isValidString", "Bitte eine gültige ISIN angeben.", errorMessages, errorFlags);
        Checks.checkFieldLenght(Wert.aktie, 12,12,"isValidStringLaenge", "Die ISIN muss 12 Stellen lang sein.", errorMessages, errorFlags);
        Checks.checkField(Wert.kurs, "isValidFloat", "Bitte einen gültigen Kurs angeben.", errorMessages, errorFlags);
        Checks.checkFieldLenghtFloat(Wert.kurs, EinstellungenAktienverlaufListener.getEinstellungFloat("minAktienkurs"), Float.MAX_VALUE,
                "isValidFloatVonBis", "Der Kurs muss mindestens " +
                        EinstellungenAktienverlaufListener.getEinstellungFloat("minAktienkurs") +  " sein.", errorMessages, errorFlags);
        Checks.checkField(Wert.kassenbestand, "isValidFloatNull", "Bitte einen gültigen Kassenbestand angeben.", errorMessages, errorFlags);
        Checks.checkFieldLenghtFloat(Wert.kassenbestand, EinstellungenAktienverlaufListener.getEinstellungFloat("minDividendeRunde"),
                EinstellungenAktienverlaufListener.getEinstellungFloat("maxDividendeRunde"),
                "isValidFloatVonBis", "Der Kassenbestand muss mindestens " +
                        EinstellungenAktienverlaufListener.getEinstellungFloat("minDividendeRunde") +
                        " und maximal " + EinstellungenAktienverlaufListener.getEinstellungFloat("maxDividendeRunde") +
                        " sein.", errorMessages, errorFlags);
    }

    @Override
    protected void fillFields() {
        eingabeAktieIsin = Wert.aktie.getTextfield();
        eingabeKurs = Float.valueOf(Wert.kurs.getTextfield());
        eingabeKassenbestand = Float.valueOf(Wert.kassenbestand.getTextfield());
    }

    @Override
    protected void nextChecks() {
        // Prüfung, ob Element in der Liste vorhanden ist.
        if(checkElementAlreadyInList(eingabeAktieIsin)){
            errorMessages.add("Das Element befindet sich bereits in der ElementListe. Bitte einen anderen Datensatz angeben.");
        }
        // Prüfung, ob Element bereits in Datenbank vorhanden ist
        // Runde wird doppelt angegeben, damit keine neue Methode angelegt werden muss.
        if(SQL.checkElementAlreadyInDatenbankIntegerIntegerString(Start.getAktuelleRunde(), Start.getAktuelleRunde(), eingabeAktieIsin, "Runde", "Runde", "AktieIsin","Aktienverlauf")){
            errorMessages.add("Das Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }
        if(SQLSpiel.getOneFloat("SELECT Kapital FROM Kapitalverlauf " +
                "WHERE Runde = 0 AND AktieISIN = " + "'" + eingabeAktieIsin + "'") == 0){
            errorMessages.add("Zu der Aktie " + eingabeAktieIsin + " ist kein Startkurs vorhanden. Bitte zuerst einen erfassen.");
        }
    }

    // Prüfung, ob der Wert bereits in der Liste vorhanden ist
    public static boolean checkElementAlreadyInList(String Isin){
        boolean inList = false;
        for(ElementAktienverlauf Wert: WertList){
            if (Isin.equals(Wert.getAktie())){
                // System.out.println("Bereits vorhanden");
                inList = true;
            }
        }
        return inList;
    }

    @Override
    protected void elementInList() {
        // Element der Liste hinzufügen
        ElementAktienverlauf wert = new ElementAktienverlauf(Start.getAktuelleRunde(), eingabeAktieIsin, EinstellungenAktienListener.getEinstellungInteger("maxAnzahlAktien"), eingabeKurs, eingabeKassenbestand);
        WertList.add(wert);
        // Nach Hinzufügen die Felder leeren
        felderLeeren();
    }

    @Override
    protected boolean checkFieldsfilled() {
        Boolean filled = true;
        if(!Checks.checkOneFieldfilled(Wert.aktie) && !Checks.checkOneFieldfilled(Wert.kurs) && !Checks.checkOneFieldfilled(Wert.kassenbestand)) {
            filled = false;
        }
        // System.out.println("filled: " +  filled);
        return filled;
    }

    @Override
    protected boolean checkElementInList() {
        Boolean action = Checks.checkElementInList(WertList);
        return action;
    }

    @Override
    protected void elementInsert() {
        // Option 1: Liste Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert ausgeben.
        // Kein Eingriff bei in der Zwischenzeit geänderten Daten.
        if(SQLAktienverlauf.selectInsertTableAktienverlauf(WertList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Diese wurden nicht erfasst. Der Rest wurde verarbeitet.");
        }
        // TODO: Option 2 prüfen, siehe AktienListener
    }

    @Override
    protected void clearliste() {
        WertList.clear();
    }

    @Override
    protected void felderLeeren(){
        Checks.clearOneField(Wert.aktie);
        Checks.clearOneField(Wert.kurs);
        Checks.clearOneField(Wert.kassenbestand);
    }

    @Override
    protected void changePanel() {
        Cards.changeCard(Cards.nameStart);
    }
}