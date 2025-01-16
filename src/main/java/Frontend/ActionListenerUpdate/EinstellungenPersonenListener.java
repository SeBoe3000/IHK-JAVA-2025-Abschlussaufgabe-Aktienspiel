package Frontend.ActionListenerUpdate;

import Datenbank.SQLEinstellungen;
import Frontend.Checks.Checks;
import Frontend.Komponenten.Interaction;
import Frontend.Programme.Konfiguration.EinstellungenPersonen;

import javax.swing.*;

public class EinstellungenPersonenListener extends MyActionListenerUpdate {
    public EinstellungenPersonenListener(JButton Btn) {
        super(Btn);
    }

    public static Integer defaultStrartkapital;

    static Integer eingabeDefaultStrartkapital;

    public static void setDefaults() {
        String einstellung = SQLEinstellungen.getEinstellung("PER");
        try {
            defaultStrartkapital = Integer.valueOf(einstellung);
            EinstellungenPersonen.defaultStrartkapital.setTextField(String.valueOf(defaultStrartkapital));
        } catch (Exception e) {
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    @Override
    protected void checkfields() {
        Checks.checkField(EinstellungenPersonen.defaultStrartkapital, "isValidInteger", "Bitte einen gültiges Startkapital angeben.", errorMessages);
    }

    @Override
    protected void fillFields(){
        eingabeDefaultStrartkapital = Integer.valueOf(EinstellungenPersonen.defaultStrartkapital.getTextfield());
    }

    @Override
    protected Boolean checkChanged() {
        System.out.println("defaultStrartkapital: " + defaultStrartkapital + " eingabeDefaultStrartkapital: " + eingabeDefaultStrartkapital);
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation werden beide Felder in einen String und zurück in Integer/Float geparsed.
        // Feld 1 wird aus Datenbank und Feld 2 aus Textfeld als String ermittelt und in einen Integer/Float geparsed.
        if(Integer.parseInt(String.valueOf(defaultStrartkapital)) != Integer.parseInt(String.valueOf(eingabeDefaultStrartkapital))){
            check = true;
        }
        System.out.println("Ergebnis Person: " + check);
        return check;
    }

    @Override
    protected void insertUpdateEinstellungen() {
        String update = String.valueOf(eingabeDefaultStrartkapital);
        SQLEinstellungen.setEinstellung("PER", update);
    }
}
