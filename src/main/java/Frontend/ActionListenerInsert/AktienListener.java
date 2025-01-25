package Frontend.ActionListenerInsert;

import Backend.ElementAktie;
import Datenbank.SQL;
import Datenbank.SQLAktien;
import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Programme.Stammdaten.Aktie;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AktienListener extends MyActionListenerInsert {
    public AktienListener(JButton Btn) {
        super(Btn);
    }

    // Zum Speichern der Ergebnisse
    public static ArrayList<ElementAktie> AktieList = new ArrayList<>();

    // Zu füllende Felder
    static String eingabeIsin = "";
    String eingabeName = "";

    @Override
    public void checkFields(){
        Checks.checkField(Aktie.isin, "isValidString", "Bitte eine gültige ISIN angeben.", errorMessages, errorFlags);
        Checks.checkFieldLenght(Aktie.isin, 12,12,"isValidStringLaenge", "Die ISIN muss 12 Stellen lang sein.", errorMessages, errorFlags);
        Checks.checkField(Aktie.name, "isValidString", "Bitte einen gültigen Namen angeben.", errorMessages, errorFlags);
        Checks.checkFieldLenght(Aktie.name, 0,50,"isValidStringLaenge", "Der Name kann bis zu 50 stellen lang sein.", errorMessages, errorFlags);

        /* Kann auch wie folgt definiert werden:
        if(!Checks.checkValues(Aktie.isin, "isValidString", 0,0)){
            errorMessages.add("Bitte eine ISIN angeben.");
        }*/
    }

    @Override
    public void fillFields(){
        eingabeIsin = Aktie.isin.getTextfield();
        eingabeName = Aktie.name.getTextfield();
    }

    @Override
    public void nextChecks() {
        // Prüfung, ob Element in der Liste vorhanden ist.
        if(checkElementAlreadyInList(eingabeIsin)){
            errorMessages.add("Das Element befindet sich bereits in der ElementListe. Bitte einen anderen Datensatz angeben.");
        }
        // Prüfung, ob Element bereits in Datenbank vorhanden ist
        if(SQL.checkElementAlreadyInDatenbankOneString(eingabeIsin, "isin","Aktien")){
            errorMessages.add("Das Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }
    }

    // Prüfung, ob der Wert bereits in der Liste vorhanden ist
    public static boolean checkElementAlreadyInList(String Isin){
        boolean inList = false;
        for(ElementAktie Aktie: AktieList){
            if (Isin.equals(Aktie.getIsin())){
                // System.out.println("Bereits vorhanden");
                inList = true;
            }
        }
        return inList;
    }

    @Override
    public void elementInList(){
        // Element der Liste hinzufügen
        ElementAktie aktie = new ElementAktie(eingabeIsin, eingabeName);
        AktieList.add(aktie);
        // Nach Hinzufügen die Felder leeren
        felderLeeren();
    }

    @Override
    protected boolean checkFieldsfilled() {
        Boolean filled = true;
        if(!Checks.checkOneFieldfilled(Aktie.isin) && !Checks.checkOneFieldfilled(Aktie.name)) {
            filled = false;
        }
        // System.out.println("filled: " +  filled);
        return filled;
    }

    @Override
    protected boolean checkElementInList() {
        Boolean action = Checks.checkElementInList(AktieList);
        return action;
    }

    @Override
    protected void elementInsert() {
        // Option 1: Liste Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert ausgeben.
        // Kein Eingriff bei in der Zwischenzeit geänderten Daten.
        if(SQLAktien.selectInsertTableAktie(AktieList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Diese wurden nicht erfasst. Der Rest wurde verarbeitet.");
        }

        /*
        // Option 2: Datensätze nur verarbeiten, wenn Elemente in der Zwischenzeit nicht in Datenbank erfasst wurden
        // TODO: Daten nachträglich bearbeitbar machen (Liste anzeigen)
        // TODO: Rückgabe, damit Dialog nur bei erfolgreicher Verarbeitung geschlossen wird
        // TODO: prüfen, ob Select-SQL gesammelt im Batch verarbeitet werden könnte
        if(checkInDatenbank(AktieList)) {
            JOptionPane.showMessageDialog(null, "Datensatz aus ElementListe ist bereits in Datenbank vorhanden.");
        } else {
            SQLAktien.insertTableAktie(AktieList);
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        }
        */
    }

    // prüfen, ob Element aus ElementListe in Datenbank vorhanden ist
    // Für Option 2 im AktienListener.elementInsert benötigt.
    public static Boolean checkInDatenbank(List<ElementAktie> aktie){
        Boolean inDatenbank = false;
        for(ElementAktie Aktie: aktie){
            if(SQL.checkElementAlreadyInDatenbankOneString(eingabeIsin, "isin","Aktien")){
                inDatenbank = true;
                break;
            }
        }
        return inDatenbank;
    }

    @Override
    protected void clearliste() {
        AktieList.clear();
    }

    @Override
    protected void felderLeeren(){
        Checks.clearOneField(Aktie.isin);
        Checks.clearOneField(Aktie.name);
    }

    @Override
    protected void changePanel() {
        Cards.changeCard(Cards.nameStammdaten);
    }
}
