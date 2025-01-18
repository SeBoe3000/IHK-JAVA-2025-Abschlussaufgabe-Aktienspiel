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

    public static String defaultStrartkapital;

    static String eingabeDefaultStrartkapital;

    public static void setDefaults() {
        getDefaults();

        EinstellungenPersonen.defaultStrartkapital.setTextField(defaultStrartkapital);
    }

    protected static void getDefaults(){
        String einstellung = SQLEinstellungen.getEinstellung("PER");
        try {
            defaultStrartkapital = einstellung;
        } catch (Exception e) {
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    public static Float getEinstellungFloat(String field){
        Float einstellung = 0F;
        getDefaults();

        if(field == "defaultStrartkapital"){
            einstellung = Float.valueOf(defaultStrartkapital);
        }
        return einstellung;
    }

    @Override
    protected void checkfields() {
        Checks.checkField(EinstellungenPersonen.defaultStrartkapital, "isValidFloat", "Bitte einen gültiges Startkapital angeben.", errorMessages);
    }

    @Override
    protected void fillFields(){
        eingabeDefaultStrartkapital = EinstellungenPersonen.defaultStrartkapital.getTextfield();
    }

    @Override
    protected Boolean checkChanged() {
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation (Datenbank und Textfeld), werden Srings verglichen
        if(defaultStrartkapital != eingabeDefaultStrartkapital){
            check = true;
        }
        return check;
    }

    @Override
    protected void insertUpdateEinstellungen() {
        String update = eingabeDefaultStrartkapital;
        SQLEinstellungen.setEinstellung("PER", update);
    }
}
