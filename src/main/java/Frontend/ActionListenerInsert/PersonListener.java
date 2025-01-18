package Frontend.ActionListenerInsert;

import Backend.ElementPerson;
import Datenbank.SQL;
import Datenbank.SQLPerson;
import Frontend.Cards;
import Frontend.Checks.Checks;
import Frontend.Programme.Stammdaten.Person;

import javax.swing.*;
import java.util.ArrayList;

public class PersonListener extends MyActionListenerInsert {
    public PersonListener(JButton Btn) {
        super(Btn);
    }

    // Zum Speichern der Ergebnisse
    public static ArrayList<ElementPerson> PersonList = new ArrayList<>();

    // Zu füllende Felder
    String eingabeVorname = "";
    String eingabeNachname = "";
    Integer eingabeAlter = 0;

    @Override
    protected void checkFields() {
        Checks.checkField(Person.vorname, "isValidString", "Bitte einen gültigen Vorname angeben.", errorMessages);
        Checks.checkFieldLenght(Person.vorname, 0,20,"isValidStringLaenge", "Der Vorname darf maximal 20 Stellen lang sein.", errorMessages);
        Checks.checkField(Person.nachname, "isValidString", "Bitte einen gültigen Nachname angeben.", errorMessages);
        Checks.checkFieldLenght(Person.nachname, 0,20,"isValidStringLaenge", "Der Nachname darf maximal 20 Stellen lang sein.", errorMessages);
        Checks.checkFieldLenght(Person.alter, 18,130,"isValidIntegerVonBis", "Bitte ein Alter zwischen 18 und 130 angeben.", errorMessages);
    }

    @Override
    protected void fillFields() {
        eingabeVorname = Person.vorname.getTextfield();
        eingabeNachname = Person.nachname.getTextfield();
        eingabeAlter = Integer.valueOf(Person.alter.getTextfield());
    }

    @Override
    protected void nextChecks() {
        // Prüfung, ob Element in der Liste vorhanden ist.
        if(checkElementAlreadyInList(eingabeVorname, eingabeNachname, eingabeAlter)){
            errorMessages.add("Das Element befindet sich bereits in der ElementListe. Bitte einen anderen Datensatz angeben.");
        }
        // Prüfung, ob Element bereits in Datenbank vorhanden ist
        if(SQL.checkElementAlreadyInDatenbankStringStringInteger(eingabeVorname, eingabeNachname, eingabeAlter, "Vorname", "Nachname", "Alter","Personen")){
            errorMessages.add("Die Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }
    }

    // Prüfung, ob der Wert bereits in der Liste vorhanden ist
    public static boolean checkElementAlreadyInList(String vorname, String nachname, Integer alter){
        boolean inList = false;
        for(ElementPerson Person: PersonList){
            if (vorname.equals(Person.getVorname()) &&
                    nachname.equals(Person.getNachname()) &&
                    alter.equals(Person.getAlter())){
                // System.out.println("Bereits vorhanden");
                inList = true;
            }
        }
        return inList;
    }

    @Override
    protected void elementInList() {
        // Element der Liste hinzufügen
        ElementPerson aktie = new ElementPerson(eingabeVorname, eingabeNachname, eingabeAlter);
        PersonList.add(aktie);
        // Nach Hinzufügen die Felder leeren
        felderLeeren();
    }

    @Override
    protected boolean checkFieldsfilled() {
        Boolean filled = true;
        if(!Checks.checkOneFieldfilled(Person.vorname) && !Checks.checkOneFieldfilled(Person.nachname) && !Checks.checkOneFieldfilled(Person.alter)) {
            filled = false;
        }
        // System.out.println("filled: " +  filled);
        return filled;
    }

    @Override
    protected boolean checkElementInList() {
        Boolean action = Checks.checkElementInList(PersonList);
        return action;
    }

    @Override
    protected void elementInsert() {
        // Option 1: Liste Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert ausgeben.
        // Kein Eingriff bei in der Zwischenzeit geänderten Daten.
        if(SQLPerson.selectInsertTablePerson(PersonList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Diese wurden nicht erfasst. Der Rest wurde verarbeitet.");
        }
        // TODO: Option 2 prüfen, siehe AktienListener
    }

    @Override
    protected void clearliste() {
        PersonList.clear();
    }

    @Override
    protected void felderLeeren(){
        Checks.clearOneField(Person.vorname);
        Checks.clearOneField(Person.nachname);
        Checks.clearOneField(Person.alter);
    }

    @Override
    protected void changePanel() {
        Cards.changeCard(Cards.nameStammdaten);
    }
}
