package Frontend.ActionListenerUpdate;

import Datenbank.SQLEinstellungen;
import Frontend.Checks.Checks;
import Frontend.Komponenten.Interaction;
import Frontend.Programme.Konfiguration.EinstellungenAktienverlauf;

import javax.swing.*;

public class EinstellungenAktienverlaufListener extends MyActionListenerUpdate {
    public EinstellungenAktienverlaufListener(JButton Btn) {
        super(Btn);
    }

    public static String minDividendeRunde;
    public static String maxDividendeRunde;
    public static String minAktienkurs;

    String eingabeMinDividendeRunde;
    String eingabeMaxDividendeRunde;
    String eingabeMinAktienkurs;

    public static void setDefaults() {
        getDefaults();

        EinstellungenAktienverlauf.DividendeRunde.setTextFieldVon(minDividendeRunde);
        EinstellungenAktienverlauf.DividendeRunde.setTextFieldBis(maxDividendeRunde);
        EinstellungenAktienverlauf.minAktienkurs.setTextField(minAktienkurs);
    }

    protected static void getDefaults(){
        String einstellung = SQLEinstellungen.getEinstellung("ORD");

        int trenner1 = einstellung.indexOf(",");
        int trenner2 = einstellung.indexOf(",", trenner1 + 1);

        try {
            minDividendeRunde = einstellung.substring(0, trenner1);
            maxDividendeRunde = einstellung.substring(trenner1 + 1, trenner2);
            minAktienkurs = einstellung.substring(trenner2 + 1, einstellung.length());
        } catch (Exception e) {
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    public static Float getEinstellungFloat(String field){
        Float einstellung = 0F;
        getDefaults();
        if(field == "minDividendeRunde"){
            einstellung = Float.valueOf(minDividendeRunde);
        } else if (field == "maxDividendeRunde") {
            einstellung = Float.valueOf(maxDividendeRunde);
        } else if (field == "minAktienkurs") {
            einstellung = Float.valueOf(minAktienkurs);
        }
        return einstellung;
    }

    @Override
    protected void checkfields() {
        Checks.checkFieldVonBis(EinstellungenAktienverlauf.DividendeRunde, "isValidFloatNull", "Bitte eine gültige Anzahl an Runden angeben.", errorMessages, errorFlagsVonBis);
        Checks.checkField(EinstellungenAktienverlauf.minAktienkurs, "isValidFloat", "Bitte einen gültigen minimalen Aktienkurs angeben.", errorMessages, errorFlags);
    }

    @Override
    protected void fillFields(){
        eingabeMinDividendeRunde = EinstellungenAktienverlauf.DividendeRunde.getTextfieldVon();
        eingabeMaxDividendeRunde = EinstellungenAktienverlauf.DividendeRunde.getTextfieldBis();
        eingabeMinAktienkurs = EinstellungenAktienverlauf.minAktienkurs.getTextfield();
    }

    @Override
    protected Boolean checkChanged() {
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation (Datenbank und Textfeld), werden Strings verglichen
        if((minDividendeRunde != null && !minDividendeRunde.equals(eingabeMinDividendeRunde)) ||
                (maxDividendeRunde != null && !maxDividendeRunde.equals(eingabeMaxDividendeRunde)) ||
                (minAktienkurs != null && !minAktienkurs.equals(eingabeMinAktienkurs))){
            check = true;
        }
        return check;
    }

    @Override
    protected void insertUpdateEinstellungen() {
        String update = eingabeMinDividendeRunde + "," +
                eingabeMaxDividendeRunde + "," +
                eingabeMinAktienkurs;
        SQLEinstellungen.setEinstellung("ORD", update);
    }

    @Override
    protected void felderLeeren() {
        Checks.clearOneFieldVonBis(EinstellungenAktienverlauf.DividendeRunde);
        Checks.clearOneField(EinstellungenAktienverlauf.minAktienkurs);
    }
}
