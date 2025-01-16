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

    public static Integer maxAnzahlAktien;

    Integer eingabeMaxAnzahlAktien;

    public static void setDefaults() {
        String einstellung = SQLEinstellungen.getEinstellung("AKT");
        try {
            maxAnzahlAktien = Integer.valueOf(einstellung);
            EinstellungenAktien.maxAnzahlAktien.setTextField(String.valueOf(maxAnzahlAktien));
        } catch (Exception e) {
            Interaction.noDatabase();
            // e.printStackTrace();
        }
    }

    @Override
    protected void checkfields() {
        Checks.checkField(EinstellungenAktien.maxAnzahlAktien, "isValidInteger", "Bitte eine gültige Anzahl maximaler Aktien angeben.", errorMessages);
    }

    @Override
    protected void fillFields(){
        eingabeMaxAnzahlAktien = Integer.valueOf(EinstellungenAktien.maxAnzahlAktien.getTextfield());
    }

    @Override
    protected Boolean checkChanged() {
        Boolean check = false;
        // Aufgrund einer möglichen anderen Interpretation werden beide Felder in einen String und zurück in Integer/Float geparsed.
        // Feld 1 wird aus Datenbank und Feld 2 aus Textfeld als String ermittelt und in einen Integer/Float geparsed.
        if(Integer.parseInt(String.valueOf(maxAnzahlAktien)) != Integer.parseInt(String.valueOf(eingabeMaxAnzahlAktien))){
            check = true;
        }
        return check;
    }

    @Override
    protected void insertUpdateEinstellungen() {
        String update = String.valueOf(eingabeMaxAnzahlAktien);
        SQLEinstellungen.setEinstellung("AKT", update);
    }
}
