package Frontend.ActionListenerInsert;

import Backend.ElementAktienverlauf;
import Datenbank.SQL;
import Datenbank.SQLAktienverlauf;
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
        Checks.checkField(Wert.aktie, "isValidString", "Bitte eine gültige ISIN angeben.", errorMessages);
        Checks.checkFieldLenght(Wert.aktie, 12,12,"isValidStringLaenge", "Die ISIN muss 12 Stellen lang sein.", errorMessages);
        Checks.checkField(Wert.kurs, "isValidFloat", "Bitte einen gültigen Kurs angeben.", errorMessages);
        Checks.checkField(Wert.kassenbestand, "isValidFloat", "Bitte einen gültigen Kassenbestand angeben.", errorMessages);
        // TODO: Prüfen nur im bestimmten Rahmen möglich.
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
        if(SQL.checkElementAlreadyInDatenbankIntegerIntegerString(Start.runde, Start.runde, eingabeAktieIsin, "Runde", "Runde", "AktieIsin","Aktienverlauf")){
            errorMessages.add("Die Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
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
        ElementAktienverlauf wert = new ElementAktienverlauf(Start.runde, eingabeAktieIsin, 100, eingabeKurs, eingabeKassenbestand);
        // TODO: Eingabe Anzahl prüfen.
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