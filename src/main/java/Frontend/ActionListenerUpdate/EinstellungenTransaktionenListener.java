package Frontend.ActionListenerUpdate;

import Datenbank.SQLEinstellungen;
import Frontend.Checks.Checks;
import Frontend.Komponenten.Interaction;
import Frontend.Programme.Konfiguration.EinstellungenTransaktionen;

import javax.swing.*;

public class EinstellungenTransaktionenListener extends MyActionListenerUpdate {
    public EinstellungenTransaktionenListener(JButton Btn) {
        super(Btn);
    }

    public static String maxAktienPersonRunde;
    public static String minPersonRunde;
    public static String maxPersonRunde;
    public static String minAktieRunde;
    public static String maxAktieRunde;
    public static String firstDividende;
    public static String secondDividende;

    String eingabeMaxAktienPersonRunde;
    String eingabeMinPersonRunde;
    String eingabeMaxPersonRunde;
    String eingabeMinAktieRunde;
    String eingabeMaxAktieRunde;
    String eingabeFirstDividende;
    String eingabeSecondDividende;

    public static void setDefaults() {
        getDefaults();

        EinstellungenTransaktionen.maxAktienPersonRunde.setTextField(maxAktienPersonRunde);
        EinstellungenTransaktionen.personRunde.setTextFieldVon(minPersonRunde);
        EinstellungenTransaktionen.personRunde.setTextFieldBis(maxPersonRunde);
        EinstellungenTransaktionen.aktieRunde.setTextFieldVon(minAktieRunde);
        EinstellungenTransaktionen.aktieRunde.setTextFieldBis(maxAktieRunde);
        EinstellungenTransaktionen.firstDividende.setTextField(firstDividende);
        EinstellungenTransaktionen.secondDividende.setTextField(secondDividende);
    }

    protected static void getDefaults(){
        String einstellung = SQLEinstellungen.getEinstellung("TRN");

        int trenner1 = einstellung.indexOf(",");
        int trenner2 = einstellung.indexOf(",", trenner1 + 1);
        int trenner3 = einstellung.indexOf(",", trenner2 + 1);
        int trenner4 = einstellung.indexOf(",", trenner3 + 1);
        int trenner5 = einstellung.indexOf(",", trenner4 + 1);
        int trenner6 = einstellung.indexOf(",", trenner5 + 1);

        try {
            maxAktienPersonRunde = einstellung.substring(0, trenner1);
            minPersonRunde = einstellung.substring(trenner1 + 1, trenner2);
            maxPersonRunde = einstellung.substring(trenner2 + 1, trenner3);
            minAktieRunde = einstellung.substring(trenner3 + 1, trenner4);
            maxAktieRunde = einstellung.substring(trenner4 + 1, trenner5);
            firstDividende = einstellung.substring(trenner5 + 1, trenner6);
            secondDividende = einstellung.substring(trenner6 + 1, einstellung.length());
        } catch (Exception e) {
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    public static Integer getEinstellungInteger(String field){
        Integer einstellung = 0;
        getDefaults();

        if(field == "maxAktienPersonRunde"){
            einstellung = Integer.valueOf(maxAktienPersonRunde);
        } else if (field == "minPersonRunde") {
            einstellung = Integer.valueOf(minPersonRunde);
        } else if (field == "maxPersonRunde") {
            einstellung = Integer.valueOf(maxPersonRunde);
        } else if (field == "minAktieRunde") {
            einstellung = Integer.valueOf(minAktieRunde);
        } else if (field == "maxAktieRunde") {
            einstellung = Integer.valueOf(maxAktieRunde);
        }
        return einstellung;
    }

    public static Float getEinstellungFloat(String field){
        Float einstellung = 0F;
        getDefaults();
        if(field == "firstDividende"){
            einstellung = Float.valueOf(firstDividende);
        } else if (field == "secondDividende") {
            einstellung = Float.valueOf(secondDividende);
        }
        return einstellung;
    }

    @Override
    protected void checkfields() {
        Checks.checkField(EinstellungenTransaktionen.maxAktienPersonRunde, "isValidInteger", "Bitte eine gültige Anzahl maximaler Aktien pro Runde angeben.", errorMessages);
        Checks.checkFieldVonBis(EinstellungenTransaktionen.personRunde, "isValidIntegerNull", "Bitte eine gültige Anzahl an Personen pro Runde angeben.", errorMessages);
        Checks.checkFieldVonBis(EinstellungenTransaktionen.aktieRunde, "isValidIntegerNull", "Bitte eine gültige Anzahl an Aktien pro Runde angeben.", errorMessages);
        Checks.checkField(EinstellungenTransaktionen.firstDividende, "isValidFloatNull", "Bitte einen gültigen Prozentsatz für den ersten Platz angeben.", errorMessages);
        Checks.checkField(EinstellungenTransaktionen.secondDividende, "isValidFloatNull", "Bitte einen gültigen Prozentsatz für den zweiten Platz angeben.", errorMessages);
    }

    @Override
    protected void fillFields(){
        eingabeMaxAktienPersonRunde = EinstellungenTransaktionen.maxAktienPersonRunde.getTextfield();
        eingabeMinPersonRunde = EinstellungenTransaktionen.personRunde.getTextfieldVon();
        eingabeMaxPersonRunde = EinstellungenTransaktionen.personRunde.getTextfieldBis();
        eingabeMinAktieRunde = EinstellungenTransaktionen.aktieRunde.getTextfieldVon();
        eingabeMaxAktieRunde = EinstellungenTransaktionen.aktieRunde.getTextfieldBis();
        eingabeFirstDividende = EinstellungenTransaktionen.firstDividende.getTextfield();
        eingabeSecondDividende = EinstellungenTransaktionen.secondDividende.getTextfield();
    }

    @Override
    protected Boolean checkChanged() {
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation werden beide Felder in einen String und zurück in Integer/Float geparsed.
        // Feld 1 wird aus Datenbank und Feld 2 aus Textfeld als String ermittelt und in einen Integer/Float geparsed.
        if(eingabeMaxAktienPersonRunde != maxAktienPersonRunde ||
                eingabeMinPersonRunde != minPersonRunde ||
                eingabeMaxPersonRunde != maxPersonRunde ||
                eingabeMinAktieRunde != minAktieRunde ||
                eingabeMaxAktieRunde != maxAktieRunde ||
                eingabeFirstDividende != firstDividende ||
                eingabeSecondDividende != secondDividende){
            check = true;
        }
        return check;
    }

    @Override
    protected void insertUpdateEinstellungen() {
        String update = eingabeMaxAktienPersonRunde + "," +
                eingabeMinPersonRunde  + "," +
                eingabeMaxPersonRunde  + "," +
                eingabeMinAktieRunde  + "," +
                eingabeMaxAktieRunde  + "," +
                eingabeFirstDividende  + "," +
                eingabeSecondDividende;
        SQLEinstellungen.setEinstellung("TRN", update);
    }
}