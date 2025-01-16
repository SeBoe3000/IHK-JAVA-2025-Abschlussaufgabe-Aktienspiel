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

    public static Integer maxAktienPersonRunde;
    public static Integer minPersonRunde;
    public static Integer maxPersonRunde;
    public static Integer minAktieRunde;
    public static Integer maxAktieRunde;
    public static Float firstDividende;
    public static Float secondDividende;

    Integer eingabeMaxAktienPersonRunde;
    Integer eingabeMinPersonRunde;
    Integer eingabeMaxPersonRunde;
    Integer eingabeMinAktieRunde;
    Integer eingabeMaxAktieRunde;
    Float eingabeFirstDividende;
    Float eingabeSecondDividende;

    public static void setDefaults() {
        String einstellung = SQLEinstellungen.getEinstellung("TRN");

        int trenner1 = einstellung.indexOf(",");
        int trenner2 = einstellung.indexOf(",", trenner1 + 1);
        int trenner3 = einstellung.indexOf(",", trenner2 + 1);
        int trenner4 = einstellung.indexOf(",", trenner3 + 1);
        int trenner5 = einstellung.indexOf(",", trenner4 + 1);
        int trenner6 = einstellung.indexOf(",", trenner5 + 1);

        try {
            maxAktienPersonRunde = Integer.valueOf(einstellung.substring(0, trenner1));
            minPersonRunde = Integer.valueOf(einstellung.substring(trenner1 + 1, trenner2));
            maxPersonRunde = Integer.valueOf(einstellung.substring(trenner2 + 1, trenner3));
            minAktieRunde = Integer.valueOf(einstellung.substring(trenner3 + 1, trenner4));
            maxAktieRunde = Integer.valueOf(einstellung.substring(trenner4 + 1, trenner5));
            firstDividende = Float.valueOf(einstellung.substring(trenner5 + 1, trenner6));
            secondDividende = Float.valueOf(einstellung.substring(trenner6 + 1, einstellung.length()));
            EinstellungenTransaktionen.maxAktienPersonRunde.setTextField(String.valueOf(maxAktienPersonRunde));
            EinstellungenTransaktionen.personRunde.setTextFieldVon(String.valueOf(minPersonRunde));
            EinstellungenTransaktionen.personRunde.setTextFieldBis(String.valueOf(maxPersonRunde));
            EinstellungenTransaktionen.aktieRunde.setTextFieldVon(String.valueOf(minAktieRunde));
            EinstellungenTransaktionen.aktieRunde.setTextFieldBis(String.valueOf(maxAktieRunde));
            EinstellungenTransaktionen.firstDividende.setTextField(String.valueOf(firstDividende));
            EinstellungenTransaktionen.secondDividende.setTextField(String.valueOf(secondDividende));
        } catch (Exception e) {
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    @Override
    protected void checkfields() {
        Checks.checkField(EinstellungenTransaktionen.maxAktienPersonRunde, "isValidInteger", "Bitte eine gültige Anzahl maximaler Aktien pro Runde angeben.", errorMessages);
        Checks.checkFieldVonBis(EinstellungenTransaktionen.personRunde, "isValidInteger", "Bitte eine gültige Anzahl an Personen pro Runde angeben.", errorMessages);
        Checks.checkFieldVonBis(EinstellungenTransaktionen.aktieRunde, "isValidInteger", "Bitte eine gültige Anzahl an Aktien pro Runde angeben.", errorMessages);
        Checks.checkField(EinstellungenTransaktionen.firstDividende, "isValidFloat", "Bitte einen gültigen Prozentsatz für den ersten Platz angeben.", errorMessages);
        Checks.checkField(EinstellungenTransaktionen.secondDividende, "isValidFloat", "Bitte einen gültigen Prozentsatz für den zweiten Platz angeben.", errorMessages);
    }

    @Override
    protected void fillFields(){
        eingabeMaxAktienPersonRunde = Integer.valueOf(EinstellungenTransaktionen.maxAktienPersonRunde.getTextfield());
        eingabeMinPersonRunde = Integer.valueOf(EinstellungenTransaktionen.personRunde.getTextfieldVon());
        eingabeMaxPersonRunde = Integer.valueOf(EinstellungenTransaktionen.personRunde.getTextfieldBis());
        eingabeMinAktieRunde = Integer.valueOf(EinstellungenTransaktionen.aktieRunde.getTextfieldVon());
        eingabeMaxAktieRunde = Integer.valueOf(EinstellungenTransaktionen.aktieRunde.getTextfieldBis());
        eingabeFirstDividende = Float.valueOf(EinstellungenTransaktionen.firstDividende.getTextfield());
        eingabeSecondDividende = Float.valueOf(EinstellungenTransaktionen.secondDividende.getTextfield());
    }

    @Override
    protected Boolean checkChanged() {
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation werden beide Felder in einen String und zurück in Integer/Float geparsed.
        // Feld 1 wird aus Datenbank und Feld 2 aus Textfeld als String ermittelt und in einen Integer/Float geparsed.
        if(Integer.parseInt(String.valueOf(eingabeMaxAktienPersonRunde)) != Integer.parseInt(String.valueOf(maxAktienPersonRunde)) ||
                Integer.parseInt(String.valueOf(eingabeMinPersonRunde)) != Integer.parseInt(String.valueOf(minPersonRunde)) ||
                Integer.parseInt(String.valueOf(eingabeMaxPersonRunde)) != Integer.parseInt(String.valueOf(maxPersonRunde)) ||
                Integer.parseInt(String.valueOf(eingabeMinAktieRunde)) != Integer.parseInt(String.valueOf(minAktieRunde)) ||
                Integer.parseInt(String.valueOf(eingabeMaxAktieRunde)) != Integer.parseInt(String.valueOf(maxAktieRunde)) ||
                Float.parseFloat(String.valueOf(eingabeFirstDividende)) != Float.parseFloat(String.valueOf(firstDividende)) ||
                Float.parseFloat(String.valueOf(eingabeSecondDividende)) != Float.parseFloat(String.valueOf(secondDividende))){
            check = true;
        }
        return check;
    }

    @Override
    protected void insertUpdateEinstellungen() {
        String update = eingabeMaxAktienPersonRunde + ", " +
                eingabeMinPersonRunde  + ", " +
                eingabeMaxPersonRunde  + ", " +
                eingabeMinAktieRunde  + ", " +
                eingabeMaxAktieRunde  + ", " +
                eingabeFirstDividende  + ", " +
                eingabeSecondDividende;
        SQLEinstellungen.setEinstellung("TRN", update);
    }
}