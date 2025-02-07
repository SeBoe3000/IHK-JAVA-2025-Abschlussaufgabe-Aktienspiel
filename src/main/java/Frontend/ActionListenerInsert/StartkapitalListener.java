package Frontend.ActionListenerInsert;

import Backend.ElementKapitalverlauf;
import Datenbank.SQL;
import Datenbank.SQLKapitalverlauf;
import Frontend.ActionListenerUpdate.EinstellungenPersonenListener;
import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Programme.Stammdaten.Startkapital;

import javax.swing.*;
import java.util.ArrayList;

public class StartkapitalListener extends MyActionListenerInsert {
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
        Checks.checkField(Startkapital.person, "isValidInteger", "Bitte eine gültige Zahl im Feld Person (ID) angeben.", errorMessages, errorFlags);
        Checks.checkField(Startkapital.betrag, "isValidFloat", "Bitte einen gültigen Betrag angeben.", errorMessages, errorFlags);
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
        if(checkElementAlreadyInList(StartkapitalList,eingabePersonID)){
            errorMessages.add("Das Element befindet sich bereits in der ElementListe. Bitte einen anderen Datensatz angeben.");
        }
        // Prüfung, ob Element bereits in Datenbank vorhanden ist
        if(SQL.checkElementAlreadyInDatenbankIntegerInteger(eingabePersonID, 0, "Personid", "Runde", "Kapitalverlauf")){
            errorMessages.add("Das Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }
    }

    // Prüfung, ob der Wert bereits in der Liste vorhanden ist
    public static boolean checkElementAlreadyInList(ArrayList<ElementKapitalverlauf> StartkapitalList, Integer personID){
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
    }

    @Override
    protected boolean checkFieldsfilled() {
        Boolean filled = true;
        // Betrag zählt nur als filled, wenn Default bearbeitbar und das Feld geändert wurde ist.
        if(EinstellungenPersonenListener.getEinstellungString("defaultStrartkapitalBearbeitbar").equals("J") &&
                !String.valueOf(EinstellungenPersonenListener.getEinstellungFloat("defaultStrartkapital")).
                        equals((Startkapital.betrag.getTextfield()))){
            if (!Checks.checkOneFieldfilled(Startkapital.person) && !Checks.checkOneFieldfilled(Startkapital.betrag)){
                filled = false;
            }
        } else {
            if (!Checks.checkOneFieldfilled(Startkapital.person)){
                filled = false;
            }
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

    @Override
    protected void clearliste() {
        StartkapitalList.clear();
    }

    @Override
    protected void felderLeeren(){
        Checks.clearOneField(Startkapital.person);
        Checks.clearOneField(Startkapital.betrag);
        setDefaultStartkapitalInBetrag();
    }

    public static void setDefaultStartkapitalInBetrag(){
        if(EinstellungenPersonenListener.getEinstellungFloat("defaultStrartkapital") != null) {
            Startkapital.betrag.setTextField(String.valueOf(EinstellungenPersonenListener.getEinstellungFloat("defaultStrartkapital")));
        }
    }

    @Override
    protected void changePanel() {
        Cards.changeCard(Cards.nameStammdaten);
    }
}
