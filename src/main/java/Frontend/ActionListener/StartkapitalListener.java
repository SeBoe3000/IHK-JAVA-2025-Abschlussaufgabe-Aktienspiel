package Frontend.ActionListener;

import Backend.ElementKapitalverlauf;
import Datenbank.SQL;
import Datenbank.SQLKapitalverlauf;
import Frontend.Cards;
import Frontend.Programme.Stammdaten.Startkapital;

import javax.swing.*;
import java.util.ArrayList;

import static Frontend.Cards.cardLayout;

public class StartkapitalListener extends MyActionListener{
    public StartkapitalListener(JButton Btn) {
        super(Btn);
    }

    // Zum Speichern der Ergebnisse
    public static ArrayList<ElementKapitalverlauf> StartkapitalList = new ArrayList<>();

    // Zu füllende Felder
    static Integer eingabePersonID = 0;
    Float eingabeBetrag = 0F;

    @Override
    protected void checkFields() {
        Checks.checkField(Startkapital.person, "isValidInteger", "Bitte eine gültige Zahl im Fled Person (ID) angeben.", errorMessages);
        Checks.checkField(Startkapital.betrag, "isValidFloat", "Bitte einen gültigen Betrag angeben.", errorMessages);

    }

    @Override
    protected void fillFields() {
        eingabePersonID = Integer.valueOf(Startkapital.person.getTextfield());
        eingabeBetrag = Float.valueOf(Startkapital.betrag.getTextfield());
    }

    @Override
    protected void nextChecks() {
        // PersonID muss in Tabelle Personen vorhanden sein.
        if(!SQL.checkElementAlreadyInDatenbankOneInteger(eingabePersonID, "id","Personen")){
            errorMessages.add("Die ausgewählte Person ist nicht in der Tabelle Personen vorhanden.");
        }

        // Prüfung, ob Element in der Liste vorhanden ist.
        if(checkElementAlreadyInList(eingabePersonID)){
            errorMessages.add("Das Element befindet sich bereits in der ElementListe. Bitte einen anderen Datensatz angeben.");
        }
        // Prüfung, ob Element bereits in Datenbank vorhanden ist
        // TODO: nur auf die Runde 0 beziehen? prüfen.
        if(SQL.checkElementAlreadyInDatenbankOneInteger(eingabePersonID, "id","Kapitalverlauf")){
            errorMessages.add("Die Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }
    }

    // TODO: prüfen, ob Auslagerung möglich ist.
    // Prüfung, ob der Wert bereits in der Liste vorhanden ist
    public static boolean checkElementAlreadyInList(Integer personID){
        boolean inList = false;
        for(ElementKapitalverlauf Startkapital: StartkapitalList){
            if (personID.equals(Startkapital.getPerson())){
                // System.out.println("Bereits vorhanden");
                inList = true;
            }
        }
        return inList;
    }

    @Override
    protected void elementInList() {
        // Element der Liste hinzufügen
        ElementKapitalverlauf startkapital = new ElementKapitalverlauf(0, eingabePersonID, eingabeBetrag);
        StartkapitalList.add(startkapital);
        // Nach Hinzufügen die Felder leeren
        felderLeeren();
        // Finaler Check kennzeichnen
    }

    @Override
    protected boolean checkFieldsfilled() {
        Boolean filled = true;
        if (!Checks.checkOneFieldfilled(Startkapital.person) && !Checks.checkOneFieldfilled(Startkapital.betrag)) {
            filled = false;
        }
        // System.out.println("filled: " +  filled);
        return filled;
    }


    @Override
    protected boolean checkElementInList() {
        Boolean action = Checks.checkElementInList(StartkapitalList);
        return action;
    }

    @Override
    protected void elementInsert() {
        // Option 1: Liste Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert ausgeben.
        // Kein Eingriff bei in der Zwischenzeit geänderten Daten.
        if(SQLKapitalverlauf.selectInsertTableKapitalverlauf(StartkapitalList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Diese wurden nicht erfasst. Der Rest wurde verarbeitet.");
        }
    }

    // Feld leeren und Fehler entfernen
    public static void felderLeeren(){
        Checks.clearOneField(Startkapital.person);
        Checks.clearOneField(Startkapital.betrag);
    }

    @Override
    protected void backToStart() {
        // Arrayliste leeren
        StartkapitalList.clear();
        // Werte und Fehler in Feldern leeren, sonst sind diese beim nächsten Mal gefüllt
        felderLeeren();
        // Panel wechseln
        cardLayout.show(Cards.cardPanel, Cards.nameStammdaten);
    }
}
