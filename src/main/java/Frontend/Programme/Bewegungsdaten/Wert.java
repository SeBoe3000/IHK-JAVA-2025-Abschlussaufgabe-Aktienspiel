package Frontend.Programme.Bewegungsdaten;

import Frontend.Komponenten.Buttons;
import Frontend.Komponenten.EingabePanel;
import Frontend.Programme.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Wert {
    public static final JFrame wert = new JFrame("Unternehmenswert erfassen");

    EingabePanel aktie = new EingabePanel("Aktie (ISIN): ");
    EingabePanel kurs = new EingabePanel("Kurs: ");
    EingabePanel kassenbestand = new EingabePanel("Kassenbestand: ");

    Buttons buttons = new Buttons();

    private void wert() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Aktie hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(aktie, gbc);

        // Kurs hinzufügen
        gbc.gridy = 1; // Zeile
        panel.add(kurs, gbc);

        // Kassenbestand hinzufügen
        gbc.gridy = 2; // Zeile
        panel.add(kassenbestand, gbc);

        // Buttons hinzufügen
        gbc.gridy = 3; // Zeile
        buttons.setVisibleFalse();
        panel.add(buttons, gbc);

        // Panel dem Frame hinzufügen
        wert.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        wert.setSize(dim.width / 2, dim.height / 2);
        wert.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        wert.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        wert.setVisible(true);
    }

    private void buttonListenerstart() {
        ActionListener ok = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Prüfen, ob Element in der Liste vorhanden ist
                // TODO:Prüfen, ob Element in der Datenbank vorhanden ist
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

    // Prüfung, ob eine Feld gefüllt ist
    private boolean checkFilled(){
        Boolean checked = true;
        if(aktie.getTextfield().isEmpty() && kurs.getTextfield().isEmpty() && kassenbestand.getTextfield().isEmpty()){
            checked = false;
        }
        return checked;
    }

    // Felder leeren
    private void clearFields(){
        aktie.setTextField("");
        kurs.setTextField("");
        kassenbestand.setTextField("");
    }

    // Zu Start zurückkehren
    private void backToStart(){
        //Start.start.setVisible(true);
        wert.setVisible(false);
        // Felder leeren
        clearFields();
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                wert();
                buttonListenerstart();
            }
        });
    }
}