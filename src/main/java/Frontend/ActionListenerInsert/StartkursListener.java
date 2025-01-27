package Frontend.ActionListenerInsert;

import Backend.ElementAktienverlauf;
import Datenbank.SQL;
import Datenbank.SQLAktienverlauf;
import Frontend.ActionListenerUpdate.EinstellungenAktienListener;
import Frontend.ActionListenerUpdate.EinstellungenAktienverlaufListener;
import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Programme.Stammdaten.Startkurs;

import javax.swing.*;
import java.util.ArrayList;

public class StartkursListener extends MyActionListenerInsert {
    public StartkursListener(JButton Btn) {
        super(Btn);
    }

    // Zum Speichern der Ergebnisse
    public static ArrayList<ElementAktienverlauf> AktienverlaufList = new ArrayList<>();

    // Zu füllende Felder
    static String eingabeAktieIsin = "";
    Float eingabeStartkurs = 0F;

    @Override
    protected void checkFields() {
        Checks.checkField(Startkurs.aktie, "isValidString", "Bitte eine gültige ISIN angeben.", errorMessages, errorFlags);
        Checks.checkFieldLenght(Startkurs.aktie, 12,12,"isValidStringLaenge", "Die ISIN muss 12 Stellen lang sein.", errorMessages, errorFlags);
        Checks.checkField(Startkurs.kurs, "isValidFloat", "Bitte einen gültigen Kurs angeben.", errorMessages, errorFlags);
        Checks.checkFieldLenghtFloat(Startkurs.kurs, EinstellungenAktienverlaufListener.getEinstellungFloat("minAktienkurs"), Float.MAX_VALUE,
                "isValidFloatVonBis", "Der Kurs muss mindestens " +
                        EinstellungenAktienverlaufListener.getEinstellungFloat("minAktienkurs") +  " sein.", errorMessages, errorFlags);
    }

    @Override
    protected void fillFields() {
        eingabeAktieIsin = Startkurs.aktie.getTextfield();
        eingabeStartkurs = Float.valueOf(Startkurs.kurs.getTextfield());
    }

    @Override
    protected void nextChecks() {
        // Aktie muss in Tabelle Aktien vorhanden sein.
        if(!SQL.checkElementAlreadyInDatenbankOneString(eingabeAktieIsin, "isin","Aktien")){
            errorMessages.add("Die ausgewählte Aktie ist nicht in der Tabelle Aktien vorhanden.");
        }
        // Prüfung, ob Element in der Liste vorhanden ist.
        if(checkElementAlreadyInList(eingabeAktieIsin)){
            errorMessages.add("Das Element befindet sich bereits in der ElementListe. Bitte einen anderen Datensatz angeben.");
        }
        // Prüfung, ob Element bereits in Datenbank vorhanden ist
        // Runde wird doppelt angegeben, damit keine neue Methode angelegt werden muss.
        if(SQL.checkElementAlreadyInDatenbankIntegerIntegerString(0,0, eingabeAktieIsin, "Runde", "Runde", "AktieIsin", "Aktienverlauf")){
            errorMessages.add("Das Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }
    }

    // Prüfung, ob der Wert bereits in der Liste vorhanden ist
    public static boolean checkElementAlreadyInList(String AktieIsin){
        boolean inList = false;
        for(ElementAktienverlauf Aktienverlauf: AktienverlaufList){
            if (AktieIsin.equals(Aktienverlauf.getAktie())){
                // System.out.println("Bereits vorhanden");
                inList = true;
            }
        }
        return inList;
    }

    @Override
    protected void elementInList() {
        // Element der Liste hinzufügen
        ElementAktienverlauf aktienverlauf = new ElementAktienverlauf(0, eingabeAktieIsin, EinstellungenAktienListener.getEinstellungInteger("maxAnzahlAktien"), eingabeStartkurs, 0F);
        AktienverlaufList.add(aktienverlauf);
        // Nach Hinzufügen die Felder leeren
        felderLeeren();
    }

    @Override
    protected boolean checkFieldsfilled() {
        Boolean filled = true;
        if(!Checks.checkOneFieldfilled(Startkurs.aktie) && !Checks.checkOneFieldfilled(Startkurs.kurs)) {
            filled = false;
        }
        // System.out.println("filled: " +  filled);
        return filled;
    }

    @Override
    protected boolean checkElementInList() {
        Boolean action = Checks.checkElementInList(AktienverlaufList);
        return action;
    }

    @Override
    protected void elementInsert() {
        // Option 1: Liste Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert ausgeben.
        // Kein Eingriff bei in der Zwischenzeit geänderten Daten.
        if(SQLAktienverlauf.selectInsertTableAktienverlauf(AktienverlaufList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Diese wurden nicht erfasst. Der Rest wurde verarbeitet.");
        }
    }

    @Override
    protected void clearliste() {
        AktienverlaufList.clear();
    }

    @Override
    protected void felderLeeren(){
        Checks.clearOneField(Startkurs.aktie);
        Checks.clearOneField(Startkurs.kurs);
    }

    @Override
    protected void changePanel() {
        Cards.changeCard(Cards.nameStammdaten);
    }
}
