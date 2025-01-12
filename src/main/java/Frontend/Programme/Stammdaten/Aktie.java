package Frontend.Programme.Stammdaten;

import Backend.ElementAktie;
import Frontend.ActionListener.Aktie.AktieAbbrechenListener;
import Frontend.ActionListener.Aktie.AktieErfassenListener;
import Frontend.ActionListener.Aktie.AktieOkListener;
import Frontend.ActionListener.Checks;
import Frontend.Cards;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Datenbank.SQL.insertTableAktie;
import static Frontend.Cards.cardLayout;

public class Aktie extends JPanel{
    static EingabePanel isin = new EingabePanel("ISIN: ");
    static EingabePanel name = new EingabePanel("Name der Aktie: ");

    static Buttons buttons = new Buttons();

    // Zum Speichern der Ergebnisse
    public static ArrayList<ElementAktie> AktieList = new ArrayList<>();

    public Aktie(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // ISIN hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(isin, gbc);

        // Name hinzufügen
        gbc.gridy = 1; // Zeile
        add(name, gbc);

        // Buttons hinzufügen
        gbc.gridy = 2; // Zeile
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {
        AktieErfassenListener erfassen = new AktieErfassenListener() {
        };buttons.create_btn.addActionListener(erfassen);

        AktieOkListener ok = new AktieOkListener() {
        };buttons.ok_btn.addActionListener(ok);

        AktieAbbrechenListener abbrechen = new AktieAbbrechenListener() {
        };buttons.cancel_btn.addActionListener(abbrechen);

        ActionListener zurueckStart = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                cardLayout.show(Cards.cardPanel, "panelStart");
            }
        };
        buttons.backstart.addActionListener(zurueckStart);
    }

    public static boolean elementHinzu(){
        boolean inWork = true;
        ArrayList<String> errorMessages = new ArrayList<>(); // Zum Sammeln der Fehlermeldungen
        String eingabeIsin = "";
        String eingabeName = "";

        // Eingaben prüfen
        Checks.checkField(isin, "isValidString", "Bitte eine ISIN angeben.", errorMessages);
        Checks.checkField(name, "isValidString", "Bitte einen Namen angeben.", errorMessages);

        /* Kann auch wie folgt definiert werden:
        if(!Checks.checkValues(isin, "isValidString")){
            errorMessages.add("Bitte eine ISIN angeben.");
        }*/

        // Sofern keine Fehler vorliegen Eingaben übertragen und weitere Prüfungen durchführen
        if(errorMessages.isEmpty()) {
            eingabeIsin = isin.getTextfield();
            eingabeName = name.getTextfield();

            // Prüfung, ob Element in der Liste vorhanden ist.
            if(checkElementAlreadyInList(eingabeIsin)){
                errorMessages.add("Das Element befindet sich bereits in der ElementListe. Bitte einen anderen Datensatz angeben.");
            }

            // Prüfung, ob Element bereits in Datenbank vorhanden ist
            if(Checks.checkElementAlreadyInDatenbankOneString(eingabeIsin, "isin","Aktien")){
                errorMessages.add("Die Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
            }
        }

        // Sofern weiterhin kein Fehler vorliegt, das Element der Liste hinzufügen
        if (errorMessages.isEmpty()){
            // Element der Liste hinzufügen
            ElementAktie aktie = new ElementAktie(eingabeIsin, eingabeName);
            AktieList.add(aktie);
            // Nach Hinzufügen die Felder leeren
            felderLeeren();
            // Finaler Check kennzeichnen
            inWork = false;
        }

        // Fehlermeldungen ausgeben, wenn vorhanden
        Checks.showError(errorMessages);

        return inWork;
    }

    // TODO: Prüfen, warum immer Meldung bezüglich doppelter Datensätze kommt
    public static void elementInsert(){
        // Liste der Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert wird ausgegeben.
        // Wurden in der Zwischenzeit Daten bereits erfasst (mehrbenutzerbetrieb) ist es hier nicht mehr möglich einzugreifen.
        // Dafür müssten die bereits erfassten Daten als Liste angezeigt werden und eine nachträgliche Bearbeitung der Daten möglich sein.
        if(insertTableAktie(AktieList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Diese wurden nicht erfasst. Der Rest wurde verarbeitet.");
        }
    }

    // Prüfung ob Felder gefüllt sind (sobald eines leer ist, wird false übergeben.)
    public static boolean checkFieldsfilled() {
        Boolean filled = true;
        if(Checks.checkOneFieldfilled(isin) || Checks.checkOneFieldfilled(name)) {
            filled = false;
        }
        // System.out.println("filled: " +  filled);
        return filled;
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

    // Feld leeren und Fehler entfernen
    public static void felderLeeren(){
        Checks.clearOneField(isin);
        Checks.clearOneField(name);
    }

    public static void backToStart(){
        // Arrayliste leeren
        AktieList.clear();
        // Werte und Fehler in Feldern leeren, sonst sind diese beim nächsten Mal gefüllt
        felderLeeren();
        // Panel wechseln
        cardLayout.show(Cards.cardPanel, "panelStart");
    }
}
