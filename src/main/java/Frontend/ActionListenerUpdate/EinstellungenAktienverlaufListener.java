package Frontend.ActionListenerUpdate;

import Datenbank.SQLEinstellungen;
import Frontend.Programme.Konfiguration.EinstellungenAktienverlauf;

import javax.swing.*;

public class EinstellungenAktienverlaufListener extends MyActionListenerUpdate {
    public EinstellungenAktienverlaufListener(JButton Btn) {
        super(Btn);
    }

    public static Float minDividendeRunde;
    public static Float maxDividendeRunde;
    public static Float minAktienkurs;

    public static void setDefaults() {
        String einstellung = SQLEinstellungen.getEinstellung("ORD");

        int trenner1 = einstellung.indexOf(",");
        int trenner2 = einstellung.indexOf(",", trenner1 + 1);

        // Abspeichern
        try {
            minDividendeRunde = Float.valueOf(einstellung.substring(0, trenner1));
            maxDividendeRunde = Float.valueOf(einstellung.substring(trenner1 + 1, trenner2));
            minAktienkurs = Float.valueOf(einstellung.substring(trenner2 + 1, einstellung.length()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Allgemeiner Fehler aufgetreten.", "Ups", JOptionPane.ERROR_MESSAGE);
        }

        EinstellungenAktienverlauf.DividendeRunde.setTextFieldVon(String.valueOf(minDividendeRunde));
        EinstellungenAktienverlauf.DividendeRunde.setTextFieldBis(String.valueOf(maxDividendeRunde));
        EinstellungenAktienverlauf.minAktienkurs.setTextField(String.valueOf(minAktienkurs));
    }
}
