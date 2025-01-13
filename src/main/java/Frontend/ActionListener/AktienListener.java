package Frontend.ActionListener;

import Backend.ElementAktie;
import Frontend.Cards;
import Frontend.Programme.Stammdaten.Aktie;

import javax.swing.*;
import java.util.ArrayList;

import static Datenbank.SQL.insertTableAktie;
import static Frontend.Cards.cardLayout;

public class AktienListener extends MyActionListener {
    public AktienListener(JButton Btn) {
        super(Btn);
    }

    // Zum Speichern der Ergebnisse
    public static ArrayList<ElementAktie> AktieList = new ArrayList<>();

    // Zu füllende Felder
    String eingabeIsin = "";
    String eingabeName = "";

    @Override
    public void checkFields(){
        Checks.checkField(Aktie.isin, "isValidString", "Bitte eine ISIN angeben.", errorMessages);
        Checks.checkFieldLenght(Aktie.isin, 12,12,"isValidStringLaenge", "Die ISIN muss 12 Stellen lang sein.", errorMessages);
        Checks.checkField(Aktie.name, "isValidString", "Bitte einen Namen angeben.", errorMessages);

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
        if(Checks.checkElementAlreadyInDatenbankOneString(eingabeIsin, "isin","Aktien")){
            errorMessages.add("Die Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }
    }

    // TODO: prüfen, ob Auslagerung möglich ist.
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
        // Finaler Check kennzeichnen
    }

    // Prüfung, ob ein Feld gefüllt sind (sobald eines leer ist, wird false übergeben.)
    @Override
    protected boolean checkFieldsfilled() {
        Boolean filled = true;
        if(!Checks.checkOneFieldfilled(Aktie.isin) && !Checks.checkOneFieldfilled(Aktie.name)) {
            filled = false;
        }
        System.out.println("filled: " +  filled);
        return filled;
    }

    @Override
    protected boolean checkElementInList() {
        Boolean action = Checks.checkElementInList(AktieList);
        return action;
    }

    // TODO: Prüfen, warum immer Meldung bezüglich doppelter Datensätze kommt
    @Override
    protected void elementInsert() {
        // Liste der Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert wird ausgegeben.
        // Wurden in der Zwischenzeit Daten bereits erfasst (mehrbenutzerbetrieb) ist es hier nicht mehr möglich einzugreifen.
        // Dafür müssten die bereits erfassten Daten als Liste angezeigt werden und eine nachträgliche Bearbeitung der Daten möglich sein.
        if(insertTableAktie(AktieList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Diese wurden nicht erfasst. Der Rest wurde verarbeitet.");
        }
    }

    // Feld leeren und Fehler entfernen
    public static void felderLeeren(){
        Checks.clearOneField(Aktie.isin);
        Checks.clearOneField(Aktie.name);
    }

    @Override
    protected void backToStart() {
        // Arrayliste leeren
        AktieList.clear();
        // Werte und Fehler in Feldern leeren, sonst sind diese beim nächsten Mal gefüllt
        felderLeeren();
        // Panel wechseln
        cardLayout.show(Cards.cardPanel, Cards.nameStart);
    }
}
