package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Startkapital {
    public static final JFrame startkapital = new JFrame("Startkapital erfassen");

    static EingabePanel person = new EingabePanel("Person (ID): ");
    static EingabePanel betrag = new EingabePanel("Betrag: ");

    static Buttons buttons = new Buttons();

    private void startkapital() {
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

        // Betrag hinzufügen
        gbc.gridy = 1; // Zeile
        panel.add(betrag, gbc);

        // Buttons hinzufügen
        gbc.gridy = 2; // Zeile
        panel.add(buttons, gbc);

        // Panel dem Frame hinzufügen
        startkapital.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        startkapital.setSize(dim.width / 2, dim.height / 2);
        startkapital.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        startkapital.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        startkapital.setVisible(true);
    }

    private void buttonListenerstart() {

        ActionListener ok = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        };
        buttons.ok_btn.addActionListener(ok);

        ActionListener erfassen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        };
        buttons.create_btn.addActionListener(erfassen);

        ActionListener abbrechen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                Stammdaten.stammdaten.setVisible(true);
                startkapital.setVisible(false);
            }
        };
        buttons.cancel_btn.addActionListener(abbrechen);

        ActionListener zurueckStart = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                Start.start.setVisible(true);
                startkapital.setVisible(false);
            }
        };
        buttons.backstart.addActionListener(zurueckStart);
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startkapital();
                buttonListenerstart();
            }
        });
    }
}
