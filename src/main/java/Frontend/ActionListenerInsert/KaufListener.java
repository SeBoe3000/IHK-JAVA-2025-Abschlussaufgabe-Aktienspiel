package Frontend.ActionListenerInsert;

import Backend.ElementTransaktionen;
import Datenbank.SQL;
import Datenbank.SQLTransaktionen;
import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Programme.Bewegungsdaten.Kauf;
import Frontend.Programme.Start;

import javax.swing.*;
import java.util.ArrayList;

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
        if(SQL.checkElementAlreadyInDatenbankIntegerIntegerString(Start.runde, eingabePersonID, eingabeAktie, "runde", "PersonID", "AktieISIN", "Transaktionen")){
            // TODO: ggf. den Kauf erhöhen
            errorMessages.add("Die Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }

        // TODO: Prüfung nicht mehr als 14 Aktien oder Personen in Runde verwendet.

        // TODO: Prüfung eine Person hat maximal 30 Anteile erworben (Liste und Datenbank miteinbeziehen in Rechnung)

        // TODO: Prüfung eine Person hat nicht mehr investiert, die das Kapital aus der Runde davor hergegeben hat

    }

    // TODO: prüfen, ob Auslagerung möglich ist.
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

    @Override
    protected void elementInList() {
        // Element der Liste hinzufügen
        ElementTransaktionen transaktionen = new ElementTransaktionen(Start.runde, eingabePersonID, eingabeAktie, eingabeAnzahl, 0F);
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
