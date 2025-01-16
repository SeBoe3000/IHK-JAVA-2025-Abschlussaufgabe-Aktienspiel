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

    public static Float minDividendeRunde;
    public static Float maxDividendeRunde;
    public static Float minAktienkurs;

    Float eingabeMinDividendeRunde;
    Float eingabeMaxDividendeRunde;
    Float eingabeMinAktienkurs;

    public static void setDefaults() {
        String einstellung = SQLEinstellungen.getEinstellung("ORD");

        int trenner1 = einstellung.indexOf(",");
        int trenner2 = einstellung.indexOf(",", trenner1 + 1);

        try {
            minDividendeRunde = Float.valueOf(einstellung.substring(0, trenner1));
            maxDividendeRunde = Float.valueOf(einstellung.substring(trenner1 + 1, trenner2));
            minAktienkurs = Float.valueOf(einstellung.substring(trenner2 + 1, einstellung.length()));
            EinstellungenAktienverlauf.DividendeRunde.setTextFieldVon(String.valueOf(minDividendeRunde));
            EinstellungenAktienverlauf.DividendeRunde.setTextFieldBis(String.valueOf(maxDividendeRunde));
            EinstellungenAktienverlauf.minAktienkurs.setTextField(String.valueOf(minAktienkurs));

        } catch (Exception e) {
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    @Override
    protected void checkfields() {
        Checks.checkFieldVonBis(EinstellungenAktienverlauf.DividendeRunde, "isValidFloatNull", "Bitte eine gültige Anzahl an Runden angeben.", errorMessages);
        Checks.checkField(EinstellungenAktienverlauf.minAktienkurs, "isValidFloatNull", "Bitte einen gültigen minimalen Aktienkurs angeben.", errorMessages);
    }

    @Override
    protected void fillFields(){
        eingabeMinDividendeRunde = Float.valueOf(EinstellungenAktienverlauf.DividendeRunde.getTextfieldVon());
        eingabeMaxDividendeRunde = Float.valueOf(EinstellungenAktienverlauf.DividendeRunde.getTextfieldBis());
        eingabeMinAktienkurs = Float.valueOf(EinstellungenAktienverlauf.minAktienkurs.getTextfield());
    }

    @Override
    protected Boolean checkChanged() {
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation werden beide Felder in einen String und zurück in Integer/Float geparsed.
        // Feld 1 wird aus Datenbank und Feld 2 aus Textfeld als String ermittelt und in einen Integer/Float geparsed.
        if(Float.parseFloat(String.valueOf(minDividendeRunde)) != Float.parseFloat(String.valueOf(eingabeMinDividendeRunde)) ||
                Float.parseFloat(String.valueOf(maxDividendeRunde)) != Float.parseFloat(String.valueOf(eingabeMaxDividendeRunde)) ||
                Float.parseFloat(String.valueOf(minAktienkurs)) != Float.parseFloat(String.valueOf(eingabeMinAktienkurs))){
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
}
