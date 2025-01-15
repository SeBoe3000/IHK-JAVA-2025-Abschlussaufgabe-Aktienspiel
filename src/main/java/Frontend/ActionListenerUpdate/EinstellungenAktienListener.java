package Frontend.ActionListenerUpdate;

import Datenbank.SQLEinstellungen;
import Frontend.Programme.Konfiguration.EinstellungenAktien;

import javax.swing.*;

public class EinstellungenAktienListener extends MyActionListenerUpdate {
    public EinstellungenAktienListener(JButton Btn) {
        super(Btn);
    }

    public static Integer maxAnzahlAktien;

    public static void setDefaults() {
        String einstellung = SQLEinstellungen.getEinstellung("AKT");

        try {
            maxAnzahlAktien = Integer.valueOf(einstellung);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Allgemeiner Fehler aufgetreten.", "Ups", JOptionPane.ERROR_MESSAGE);
        }

        EinstellungenAktien.maxAnzahlAktien.setTextField(einstellung);
    }
}
