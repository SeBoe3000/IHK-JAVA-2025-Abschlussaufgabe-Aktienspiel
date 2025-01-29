package Frontend.ActionListenerUpdate;

import Datenbank.SQLEinstellungen;
import Frontend.Checks.Checks;
import Frontend.Komponenten.Interaction;
import Frontend.Programme.Konfiguration.EinstellungenAktien;

import javax.swing.*;

public class EinstellungenAktienListener extends MyActionListenerUpdate {
    public EinstellungenAktienListener(JButton Btn) {
        super(Btn);
    }

    public static String maxAnzahlAktien;

    String eingabeMaxAnzahlAktien;

    public static void setDefaults() {
        getDefaults();
        EinstellungenAktien.maxAnzahlAktien.setTextField(maxAnzahlAktien);
    }

    protected static void getDefaults(){
        String einstellung = SQLEinstellungen.getEinstellung("AKT");
        try {
            maxAnzahlAktien = einstellung;
        } catch (Exception e) {
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    public static Integer getEinstellungInteger(String field){
        Integer einstellung = 0;
        getDefaults();

        if(field == "maxAnzahlAktien"){
            einstellung = Integer.valueOf(maxAnzahlAktien);
        }
        return einstellung;
    }

    @Override
    protected void checkfields() {
        Checks.checkField(EinstellungenAktien.maxAnzahlAktien, "isValidInteger", "Bitte eine gültige Anzahl maximaler Aktien angeben.", errorMessages, errorFlags);
    }

    @Override
    protected void fillFields(){
        eingabeMaxAnzahlAktien = EinstellungenAktien.maxAnzahlAktien.getTextfield();
    }

    @Override
    protected Boolean checkChanged() {
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation (Datenbank und Textfeld), werden Strings verglichen
        if(maxAnzahlAktien != null && !maxAnzahlAktien.equals(eingabeMaxAnzahlAktien)){
            check = true;
        }
        return check;
    }

    @Override
    protected void insertUpdateEinstellungen() {
        String update = eingabeMaxAnzahlAktien;
        SQLEinstellungen.setEinstellung("AKT", update);
    }

    @Override
    protected void felderLeeren() {
        Checks.clearOneField(EinstellungenAktien.maxAnzahlAktien);
    }
}
