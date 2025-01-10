package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Startkurs {
    public static final JFrame startkurs = new JFrame("Startkurs erfassen");

    static EingabePanel aktie = new EingabePanel("Aktie (ISIN): ");
    static EingabePanel kurs = new EingabePanel("Startkurs: ");

    static Buttons buttons = new Buttons();

    private void startkurs() {
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

        // Buttons hinzufügen
        gbc.gridy = 2; // Zeile
        panel.add(buttons, gbc);

        // Panel dem Frame hinzufügen
        startkurs.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        startkurs.setSize(dim.width / 2, dim.height / 2);
        startkurs.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        startkurs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        startkurs.setVisible(true);
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
                startkurs.setVisible(false);
            }
        };
        buttons.cancel_btn.addActionListener(abbrechen);

        ActionListener zurueckStart = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                Start.start.setVisible(true);
                startkurs.setVisible(false);
            }
        };
        buttons.backstart.addActionListener(zurueckStart);
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startkurs();
                buttonListenerstart();
            }
        });
    }
}
