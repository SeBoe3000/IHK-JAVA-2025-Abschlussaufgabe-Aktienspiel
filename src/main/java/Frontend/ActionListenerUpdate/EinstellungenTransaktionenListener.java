package Frontend.ActionListenerUpdate;

import Datenbank.SQLEinstellungen;
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Allgemeiner Fehler aufgetreten.", "Ups", JOptionPane.ERROR_MESSAGE);
        }

        EinstellungenTransaktionen.maxAktienPersonRunde.setTextField(String.valueOf(maxAktienPersonRunde));
        EinstellungenTransaktionen.personRunde.setTextFieldVon(String.valueOf(minPersonRunde));
        EinstellungenTransaktionen.personRunde.setTextFieldBis(String.valueOf(maxPersonRunde));
        EinstellungenTransaktionen.aktieRunde.setTextFieldVon(String.valueOf(minAktieRunde));
        EinstellungenTransaktionen.aktieRunde.setTextFieldBis(String.valueOf(maxAktieRunde));
        EinstellungenTransaktionen.firstDividende.setTextField(String.valueOf(firstDividende));
        EinstellungenTransaktionen.secondDividende.setTextField(String.valueOf(secondDividende));
    }
}
