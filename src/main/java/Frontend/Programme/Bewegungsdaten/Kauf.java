package Frontend.Programme.Bewegungsdaten;

import Frontend.ActionListenerInsert.KaufListener;
import Frontend.ActionListenerInsert.MyActionListenerInsert;
import Frontend.Checks.Checks;
import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Kauf extends JPanel{
    public static EingabePanel person = new EingabePanel("Person (ID): ");
    public static EingabePanel aktie = new EingabePanel("Aktie: ");
    public static EingabePanel anzahl = new EingabePanel("Anzahl Aktien: ");
    public static JButton calcRestwert = new JButton("Rest berechnen");
    public static EingabePanel restwert = new EingabePanel("Restwert: ");

    static Buttons buttons = new Buttons();

    public Kauf(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Person hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(person, gbc);

        // Aktie hinzufügen
        gbc.gridy = 1; // Zeile
        add(aktie, gbc);

        // Anzahl hinzufügen
        gbc.gridy = 2; // Zeile
        add(anzahl, gbc);

        // Restwert hinzufügen
        gbc.gridy = 3; // Zeile

        JPanel rest = new JPanel();
        rest.setLayout(new BoxLayout(rest, BoxLayout.X_AXIS));
        rest.add(restwert);
        rest.add(calcRestwert);
        add(rest, gbc);
        // Restwert ausgrauen, da Anzeigefeld
        restwert.setEnabledFalse();

        // Buttons hinzufügen
        gbc.gridy = 4; // Zeile
        buttons.setVisibleStartFalse();
        add(buttons, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {
        KaufListener erfassen = new KaufListener(buttons.create_btn) {
        };buttons.create_btn.addActionListener(erfassen);

        KaufListener ok = new KaufListener(buttons.ok_btn) {
        };buttons.ok_btn.addActionListener(ok);

        KaufListener abbrechen = new KaufListener(buttons.cancel_btn) {
        };buttons.cancel_btn.addActionListener(abbrechen);

        KaufListener zurueckStart = new KaufListener(buttons.backstart) {
        };buttons.backstart.addActionListener(zurueckStart);

        ActionListener restwert = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Checks.clearOneField(Kauf.restwert); // Wert aus Berechnung zuvor löschen
                // Objekt der Klasse erzeugen, um auf die Methode zuzugreifen, ohne diese statisch zu machen.
                KaufListener listener = new KaufListener();
                if(aktie.getTextfield().isEmpty() && anzahl.getTextfield().isEmpty()) {
                    Checks.checkField(Kauf.person, "isValidInteger", "Bitte eine gültige Zahl im Feld Person (ID) angeben.", listener.errorMessages, listener.errorFlags);
                    if(listener.errorMessages.isEmpty()){
                        berechnungRestwert();
                    } else {
                        listener.changeFieldFarbe(); // Feldfarbe ändern
                        Checks.showError(listener.errorMessages); // Ausgabe Fehlermeldung(en)
                    }
                } else {
                    listener.checkFields();
                    if (listener.errorMessages.isEmpty()) {
                        listener.fillFields();
                        berechnungRestwert();
                    } else {
                        listener.changeFieldFarbe(); // Feldfarbe ändern
                        Checks.showError(listener.errorMessages); // Ausgabe Fehlermeldung(en)
                    }
                }
            }
        };
        calcRestwert.addActionListener(restwert);
    }

    private static void berechnungRestwert(){
        Integer eingabePersonID = Integer.valueOf(Kauf.person.getTextfield());
        Float startkapital = KaufListener.startkapital(eingabePersonID);
        if(startkapital > 0) {
            Kauf.restwert.setTextField(String.valueOf(KaufListener.startkapital(eingabePersonID) - KaufListener.aktienkauf(eingabePersonID)));
        } else {
            JOptionPane.showMessageDialog(null, "Für die Person konnte kein Startkapital ermittelt werden.", "Berechnung nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }
}