package Frontend.ActionListenerUpdate;

import Datenbank.SQLEinstellungen;
import Frontend.Programme.Konfiguration.EinstellungenPersonen;

import javax.swing.*;

public class EinstellungenPersonenListener extends MyActionListenerUpdate {
    public EinstellungenPersonenListener(JButton Btn) {
        super(Btn);
    }

    public static Integer defaultStrartkapital;

    public static void setDefaults() {
        String einstellung = SQLEinstellungen.getEinstellung("PER");
        try {
            defaultStrartkapital = Integer.valueOf(einstellung);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Allgemeiner Fehler aufgetreten.", "Ups", JOptionPane.ERROR_MESSAGE);
        }
        EinstellungenPersonen.defaultStrartkapital.setTextField(String.valueOf(defaultStrartkapital));
    }
}
