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
    public static String defaultStrartkapitalBearbeitbar;

    static String eingabeDefaultStrartkapital;
    static String eingabeDefaultStrartkapitalBearbeitbar;

    public static void setDefaults() {
        getDefaults();

        EinstellungenPersonen.defaultStrartkapital.setTextField(defaultStrartkapital);
        if (defaultStrartkapitalBearbeitbar != null && defaultStrartkapitalBearbeitbar.equals("J")) {
            EinstellungenPersonen.defaultStartkapitalBearbeitbar.setSelected(true);
        } else {
            EinstellungenPersonen.defaultStartkapitalBearbeitbar.setSelected(false);
        }
    }

    protected static void getDefaults(){
        String einstellung = SQLEinstellungen.getEinstellung("PER");

        int trenner1 = einstellung.indexOf(",");

        try {
            defaultStrartkapital = einstellung.substring(0, trenner1);
            defaultStrartkapitalBearbeitbar = einstellung.substring(trenner1 + 1, einstellung.length());
        } catch (Exception e) {
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    public static Float getEinstellungFloat(String field){
        Float einstellung = null;
        getDefaults();

        if(field == "defaultStrartkapital" && !defaultStrartkapital.isEmpty()){
            einstellung = Float.valueOf(defaultStrartkapital);
        }
        return einstellung;
    }

    public static String getEinstellungString(String field){
        String einstellung = "";
        getDefaults();

        if(field == "defaultStrartkapitalBearbeitbar"){
            einstellung = defaultStrartkapitalBearbeitbar;
        }
        return einstellung;
    }


    @Override
    protected void checkfields() {
        Checks.checkField(EinstellungenPersonen.defaultStrartkapital, "isValidFloatEmpty", "Bitte einen gültiges Startkapital angeben.", errorMessages, errorFlags);
        System.out.println("Startkapital: " +EinstellungenPersonen.defaultStrartkapital.getTextfield() );

        // Sofern die Checkbox markiert ist, darf nicht 0 oder nichts angegeben sein.
        // Zuerst auf leere Eingabe prüfen und dann über parsen alle möglichen Variationen von 0 abdecken.
        if ((EinstellungenPersonen.defaultStrartkapital.getTextfield().isEmpty() || Double.parseDouble(EinstellungenPersonen.defaultStrartkapital.getTextfield()) == 0) &&
                !EinstellungenPersonen.defaultStartkapitalBearbeitbar.isSelected()) {
            errorMessages.add("Default bearbeitbar kann nur demarkiert werden, wenn ein Startkapital größer 0 angegeben wurde.");
        }
    }

    @Override
    protected void fillFields(){
        eingabeDefaultStrartkapital = EinstellungenPersonen.defaultStrartkapital.getTextfield();
        eingabeDefaultStrartkapitalBearbeitbar = EinstellungenPersonen.defaultStartkapitalBearbeitbar.isSelected() ? "J" : "N"; // Checkbox Zustand speichern
    }

    @Override
    protected Boolean checkChanged() {
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation (Datenbank und Textfeld), werden Strings verglichen
        if(!defaultStrartkapital.equals(eingabeDefaultStrartkapital) || !defaultStrartkapitalBearbeitbar.equals(eingabeDefaultStrartkapitalBearbeitbar)){
            check = true;
        }
        return check;
    }

    @Override
    protected void insertUpdateEinstellungen() {
        String update = eingabeDefaultStrartkapital + "," + eingabeDefaultStrartkapitalBearbeitbar;
        SQLEinstellungen.setEinstellung("PER", update);
    }

    @Override
    protected void felderLeeren() {
        Checks.clearOneField(EinstellungenPersonen.defaultStrartkapital);
    }
}
