package Frontend.Programme.Stammdaten;

import Backend.ElementAktie;
import Datenbank.Datenbankverbindung;
import Frontend.ActionListener.Aktie.AktieAbbrechenListener;
import Frontend.ActionListener.Aktie.AktieErfassenListener;
import Frontend.ActionListener.Aktie.AktieOkListener;
import Frontend.ActionListener.EingabenCheck;
import Frontend.Cards;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Datenbank.SQL.insertTableAktie;
import static Frontend.Cards.cardLayout;

public class Aktie extends JPanel{
    static EingabePanel isin = new EingabePanel("ISIN: ");
    static EingabePanel name = new EingabePanel("Name der Aktie: ");

    static Buttons buttons = new Buttons();

    // Zum Speichern der Ergebnisse
    static ArrayList<ElementAktie> AktieList = new ArrayList<>();

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
        String eingabeIsin = "";
        String eingabeName = "";
        boolean inWork = true;
        int anzahlFelderKorrekt = 0;

        // Eingaben prüfen und Felder befüllen
        if(checkValues(isin, "isValidString", "Bitte eine ISIN angeben.")){
            eingabeIsin = isin.getTextfield();

            anzahlFelderKorrekt ++;
        }
        if(checkValues(name, "isValidString", "Bitte ein Name angegeben.")){
            eingabeName = name.getTextfield();
            anzahlFelderKorrekt ++;
        }

        // System.out.println("Anzahlkorrekter Felder " + anzahlFelderKorrekt);

        if(anzahlFelderKorrekt == 2){
            boolean insertPossible = true;
            // Hier könnte auf einen doppelten Datensatz geprüft werden.
            // Beim Insert erfolgt eine Meldung, ob doppelte Datensätze dabei waren, oder nicht. Daher bleibt hier die Überprüfung aus.
            // Bei einem vorhandenen Datensatz könnte dann insertPossible auf false geändert werden und ggf. das nächste If nach außerhalb angebracht werden und true und false hier getauscht werden.

            // Prüfung, ob Element bereits in der Liste vorhanden ist, falls ja nicht hinzufügen.
            boolean inList = checkElementAlreadyInList(eingabeIsin);
            if(inList == true){
                JOptionPane.showMessageDialog(null, "Die angegebene Aktie befindet sich bereits in der ElementListe. Geben Sie eine andere Aktie an.", "Datensatz bereits in ElementListe vorhanden", JOptionPane.ERROR_MESSAGE);
                insertPossible = false;
            }

            // Prüfung, ob Element bereits in Datenbank vorhanden ist, falls ja nicht hinzufügen.
            boolean inDatenbank = checkElementAlreadyInDatenbank(eingabeIsin);
            if(inDatenbank == true){
                JOptionPane.showMessageDialog(null, "Die angegebene Aktie befindet sich bereits in der Datenbank. Geben Sie eine andere Aktie an.", "Datensatz bereits in Datenbank vorhanden", JOptionPane.ERROR_MESSAGE);
                insertPossible = false;
            }

            if(insertPossible) {
                // Element der Liste hinzufügen
                ElementAktie aktie = new ElementAktie(eingabeIsin, eingabeName);
                AktieList.add(aktie);
                // Nach Hinzufügen die Felder leeren
                felderLeeren();
                // Finaler Check kennzeichnen
                inWork = false;
            }
        }
        return inWork;
    }

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

    // Prüfung auf gültige Eingaben
    public static boolean checkValues(EingabePanel input, String checkArt, String fehlernachricht) {
        String check = input.getTextfield();
        boolean check1 = false;

        if (checkArt == "isValidString") {
            check1 = EingabenCheck.isValidString(check);
        }  else if (checkArt == "kennzeichen") {
            check1 = EingabenCheck.isValidKennzeichen(check);
        }

        // Text für Fehlermeldung
        String text = fehlernachricht;
        String title = "Fehler";

        // Bei korrekter Eingabe (z.B. nach Fehler) Schriftfarbe zurückändern.
        input.removeError();

        if (!(check1)) {
            // Beim Fehler die Schriftfarbe auf rotändern.
            input.setError();
            JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    // Prüfung ob 1 Feld gefüllt ist
    public static boolean checkFieldsfilled() {
        Boolean notInWork = true;
        if (!(isin.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - ISIN");
        }
        if (!(name.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - Name");
        }
        return notInWork;
    }

    // Prüfung, ob ein Wert in der Liste vorhanden ist.
    public static boolean checkElementInList() {
        boolean noElement = false;
        if (AktieList.isEmpty()) {
            noElement = true;
        }
        //System.out.println("noElement: " + noElement);
        return noElement;
    }

    // Prüfung, ob der Wert bereits in der Liste vorhanden ist
    public static boolean checkElementAlreadyInList(String Isin){
        boolean inList = false;
        for(ElementAktie Aktie: AktieList){
            if (Isin.equals(Aktie.getName())){
                // System.out.println("Bereits vorhanden");
                inList = true;
            }
        }
        return inList;
    }

    // Prüfung, ob der Wert bereits in der Datenbank vorhanden ist
    public static boolean checkElementAlreadyInDatenbank(String Isin){
        boolean inDatenbank = false;

        String sqlSelect = "SELECT count(*) FROM Aktien WHERE Isin = ?";
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
        ){
            pstmtSelect.setString(1, Isin);
            ResultSet resultSelect = pstmtSelect.executeQuery();
            int result = 1;
            while (resultSelect.next()) {
                result = Integer.parseInt(resultSelect.getString(1));
                // System.out.println(result);
                if (result == 1) {
                    inDatenbank = true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return inDatenbank;
    }

    // Felder nach erfolgreicher Verarbeitung oder Abbrechen leeren und Fehler entfernen
    public static void felderLeeren(){
        // Felder leeren
        isin.setTextField("");
        name.setTextField("");

        // Auch einen etwaigen Fehler aus den Feldern entfernen
        isin.removeError();
        name.removeError();
    }

    public static void backToStart(){
        // Arrayliste leeren
        AktieList.clear();
        // Werte und Fehler in Feldern leeren, sonst sind diese beim nächsten Mal gefüllt
        felderLeeren();
        // Frame start wieder anzeigen
        cardLayout.show(Cards.cardPanel, "panelStart");
    }
}
