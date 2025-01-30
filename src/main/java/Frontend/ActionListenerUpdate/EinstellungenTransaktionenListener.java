package Frontend.ActionListenerUpdate;

import Backend.Fehler;
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
    public static String generelleDividende;

    String eingabeMaxAktienPersonRunde;
    String eingabeMinPersonRunde;
    String eingabeMaxPersonRunde;
    String eingabeMinAktieRunde;
    String eingabeMaxAktieRunde;
    String eingabeFirstDividende;
    String eingabeSecondDividende;
    String eingabeGenerelleDividende;

    public static void setDefaults() {
        getDefaults();

        EinstellungenTransaktionen.maxAktienPersonRunde.setTextField(maxAktienPersonRunde);
        EinstellungenTransaktionen.personRunde.setTextFieldVon(minPersonRunde);
        EinstellungenTransaktionen.personRunde.setTextFieldBis(maxPersonRunde);
        EinstellungenTransaktionen.aktieRunde.setTextFieldVon(minAktieRunde);
        EinstellungenTransaktionen.aktieRunde.setTextFieldBis(maxAktieRunde);
        EinstellungenTransaktionen.firstDividende.setTextField(firstDividende);
        EinstellungenTransaktionen.secondDividende.setTextField(secondDividende);
        EinstellungenTransaktionen.generelleDividende.setTextField(generelleDividende);
    }

    protected static void getDefaults(){
        String einstellung = SQLEinstellungen.getEinstellung("TRN");

        int trenner1 = einstellung.indexOf(",");
        int trenner2 = einstellung.indexOf(",", trenner1 + 1);
        int trenner3 = einstellung.indexOf(",", trenner2 + 1);
        int trenner4 = einstellung.indexOf(",", trenner3 + 1);
        int trenner5 = einstellung.indexOf(",", trenner4 + 1);
        int trenner6 = einstellung.indexOf(",", trenner5 + 1);
        int trenner7 = einstellung.indexOf(",", trenner6 + 1);

        try {
            maxAktienPersonRunde = einstellung.substring(0, trenner1);
            minPersonRunde = einstellung.substring(trenner1 + 1, trenner2);
            maxPersonRunde = einstellung.substring(trenner2 + 1, trenner3);
            minAktieRunde = einstellung.substring(trenner3 + 1, trenner4);
            maxAktieRunde = einstellung.substring(trenner4 + 1, trenner5);
            firstDividende = einstellung.substring(trenner5 + 1, trenner6);
            secondDividende = einstellung.substring(trenner6 + 1, trenner7);
            generelleDividende = einstellung.substring(trenner7 + 1, einstellung.length());
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
        } else if (field == "generelleDividende") {
            einstellung = Float.valueOf(generelleDividende);
        }
        return einstellung;
    }

    @Override
    protected void checkfields() {
        Checks.checkField(EinstellungenTransaktionen.maxAktienPersonRunde, "isValidInteger", "Bitte eine gültige Anzahl maximaler Aktien pro Runde angeben.", errorMessages, errorFlags);
        Checks.checkFieldVonBis(EinstellungenTransaktionen.personRunde, "isValidInteger", "Bitte eine gültige Anzahl an Personen pro Runde angeben.", errorMessages, errorFlagsVonBis);
        Checks.checkFieldVonBis(EinstellungenTransaktionen.aktieRunde, "isValidInteger", "Bitte eine gültige Anzahl an Aktien pro Runde angeben.", errorMessages, errorFlagsVonBis);
        Checks.checkField(EinstellungenTransaktionen.firstDividende, "isValidFloatNull", "Bitte einen gültigen Prozentsatz für den ersten Platz angeben.", errorMessages, errorFlags);
        Checks.checkField(EinstellungenTransaktionen.secondDividende, "isValidFloatNull", "Bitte einen gültigen Prozentsatz für den zweiten Platz angeben.", errorMessages, errorFlags);
        Checks.checkField(EinstellungenTransaktionen.generelleDividende, "isValidFloatNull", "Bitte einen gültigen Prozentsatz für die generelle Dividende angeben.", errorMessages, errorFlags);

        if(errorMessages.isEmpty()){
            if((Float.valueOf(EinstellungenTransaktionen.firstDividende.getTextfield()) + Float.valueOf(EinstellungenTransaktionen.secondDividende.getTextfield())) > 100){
                errorMessages.add("Die Dividende für den ersten und zweiten Platz zusammen dürfen nicht mehr als 100% ergeben.");
                errorFlags.add(new Fehler(true, EinstellungenTransaktionen.firstDividende));
                errorFlags.add(new Fehler(true, EinstellungenTransaktionen.secondDividende));
            }
            if(Float.valueOf(EinstellungenTransaktionen.firstDividende.getTextfield()) < Float.valueOf(EinstellungenTransaktionen.secondDividende.getTextfield())){
                errorMessages.add("Die Dividende für den zweiten Platz darf nicht größer als die des ersten sein.");
                errorFlags.add(new Fehler(true, EinstellungenTransaktionen.firstDividende));
                errorFlags.add(new Fehler(true, EinstellungenTransaktionen.secondDividende));
            }
        }
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
        eingabeGenerelleDividende = EinstellungenTransaktionen.generelleDividende.getTextfield();
    }

    @Override
    protected Boolean checkChanged() {
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation (Datenbank und Textfeld), werden Strings verglichen
        if((maxAktienPersonRunde != null && !eingabeMaxAktienPersonRunde.equals(maxAktienPersonRunde)) ||
                (minPersonRunde != null && !eingabeMinPersonRunde.equals( minPersonRunde)) ||
                (maxPersonRunde != null && !eingabeMaxPersonRunde.equals(maxPersonRunde)) ||
                (minAktieRunde != null && !eingabeMinAktieRunde.equals(minAktieRunde)) ||
                (maxAktieRunde != null && !eingabeMaxAktieRunde.equals(maxAktieRunde)) ||
                (firstDividende != null && !eingabeFirstDividende.equals(firstDividende)) ||
                (secondDividende != null && !eingabeSecondDividende.equals(secondDividende)) ||
                (generelleDividende != null && !eingabeGenerelleDividende.equals(generelleDividende))){
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
                eingabeSecondDividende + "," +
                eingabeGenerelleDividende;
        SQLEinstellungen.setEinstellung("TRN", update);
    }

    @Override
    protected void felderLeeren() {
        Checks.clearOneField(EinstellungenTransaktionen.maxAktienPersonRunde);
        Checks.clearOneFieldVonBis(EinstellungenTransaktionen.personRunde);
        Checks.clearOneFieldVonBis(EinstellungenTransaktionen.aktieRunde);
        Checks.clearOneField(EinstellungenTransaktionen.firstDividende);
        Checks.clearOneField(EinstellungenTransaktionen.secondDividende);
        Checks.clearOneField(EinstellungenTransaktionen.generelleDividende);
    }
}