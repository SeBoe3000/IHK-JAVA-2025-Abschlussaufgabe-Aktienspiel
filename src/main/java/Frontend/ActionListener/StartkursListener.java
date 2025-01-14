package Frontend.ActionListener;

import Backend.ElementAktie;
import Backend.ElementAktienverlauf;
import Datenbank.SQL;
import Frontend.Programme.Stammdaten.Aktie;
import Frontend.Programme.Stammdaten.Startkurs;

import javax.swing.*;
import java.util.ArrayList;

public class StartkursListener extends MyActionListener{
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
        Checks.checkField(Startkurs.aktie, "isValidString", "Bitte eine gültige ISIN angeben.", errorMessages);
        Checks.checkFieldLenght(Startkurs.aktie, 12,12,"isValidStringLaenge", "Die ISIN muss 12 Stellen lang sein.", errorMessages);
        Checks.checkField(Startkurs.kurs, "isValidFloat", "Bitte einen gültigen Kurs angeben.", errorMessages);
    }

    @Override
    protected void fillFields() {
        eingabeAktieIsin = Startkurs.aktie.getTextfield();
        eingabeStartkurs = Float.valueOf(Startkurs.kurs.getTextfield());
    }

    @Override
    protected void nextChecks() {
        // Prüfung, ob Element in der Liste vorhanden ist.
        if(checkElementAlreadyInList(eingabeAktieIsin)){
            errorMessages.add("Das Element befindet sich bereits in der ElementListe. Bitte einen anderen Datensatz angeben.");
        }
        // Prüfung, ob Element bereits in Datenbank vorhanden ist
        if(SQL.checkElementAlreadyInDatenbankIntegerIntegerString(0,0, eingabeAktieIsin, "Runde", "Runde", "AktieIsin", "Aktienverlauf")){
            errorMessages.add("Die Element befindet sich bereits in der Datenbank. Bitte einen anderen Datensatz angeben.");
        }

        // TODO: Prüfung Aktie vorhanden
    }

    // TODO: prüfen, ob Auslagerung möglich ist.
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

    }

    @Override
    protected boolean checkFieldsfilled() {
        return false;
    }

    @Override
    protected boolean checkElementInList() {
        return false;
    }

    @Override
    protected void elementInsert() {

    }

    @Override
    protected void backToStart() {

    }
}
