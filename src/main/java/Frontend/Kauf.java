package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Kauf {
    public static final JFrame kauf = new JFrame("Käufe erfassen");

    static EingabePanel person = new EingabePanel("Person (ID): ");
    static EingabePanel aktie = new EingabePanel("Aktie: ");
    static EingabePanel anzahl = new EingabePanel("Anzahl Aktien: ");
    static EingabePanel restwert = new EingabePanel("Restwert: ");

    static Buttons buttons = new Buttons();

    private void kauf() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Person hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(person, gbc);

        // Aktie hinzufügen
        gbc.gridy = 1; // Zeile
        panel.add(aktie, gbc);

        // Anzahl hinzufügen
        gbc.gridy = 2; // Zeile
        panel.add(anzahl, gbc);

        // Restwert hinzufügen
        gbc.gridy = 3; // Zeile
        panel.add(restwert, gbc);

        // Buttons hinzufügen
        gbc.gridy = 4; // Zeile
        buttons.setVisibleFalse();
        panel.add(buttons, gbc);

        // Panel dem Frame hinzufügen
        kauf.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        kauf.setSize(dim.width / 2, dim.height / 2);
        kauf.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        kauf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        kauf.setVisible(true);
    }

    private void buttonListenerstart() {
        ActionListener ok = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Prüfen, ob Element in der Liste vorhanden ist
                // TODO: Prüfen, ob Element in der Datenbank vorhanden ist
                // TODO: Element der Liste hinzufügen
            }
        };
        buttons.ok_btn.addActionListener(ok);

        ActionListener create = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Prüfen, ob Element in der Liste vorhanden ist
                // TODO: Prüfen, ob Element in der Datenbank vorhanden ist
                // TODO: Element Liste hinzufügen
                // TODO: Element aus Liste in Datenbank übertragen
                // TODO: Programmschließen
            }
        };
        buttons.create_btn.addActionListener(create);

        ActionListener abbrechen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToStart();
            }
        };
        buttons.cancel_btn.addActionListener(abbrechen);
    }

    // Prüfung, ob ein Feld gefüllt ist
    private static boolean checkFilled(){
        Boolean checked = true;
        if(person.getTextfield().isEmpty() && aktie.getTextfield().isEmpty() && anzahl.getTextfield().isEmpty()){
            checked = false;
        }
        return checked;
    }

    // Felder leeren
    private static void clearFields(){
        person.setTextField("");
        aktie.setTextField("");
        anzahl.setTextField("");
    }

    // Zu Start zurückkehren
    private static void backToStart(){
        Start.start.setVisible(true);
        kauf.setVisible(false);
        // Felder leeren
        clearFields();
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                kauf();
                buttonListenerstart();
            }
        });
    }
}