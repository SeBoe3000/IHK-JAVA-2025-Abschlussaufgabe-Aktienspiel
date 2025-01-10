package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start {
    public static final JFrame start = new JFrame("Aktienspiel");

    JButton create_importExample = new JButton("Beispieldaten importieren");
    JButton open_stammdaten = new JButton("Stammdaten erfassen");
    ButtonGroup group_stammdaten = new ButtonGroup();

    JLabel bewegungsdaten = new JLabel("Bewegungsdaten");
    JButton create_kauf = new JButton("Käufe erfassen");
    JButton create_wert = new JButton("Unternehmenswerte erfassen");

    ButtonGroup group_bewegungsdaten = new ButtonGroup();
    JButton create_runde = new JButton("Nächste Runde");

    JLabel anzeige = new JLabel("Anzeige");
    JButton show_spielstand = new JButton("Spielstand anzeigen");

    private void start() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Stammdaten hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        group_stammdaten.add(create_importExample);
        group_stammdaten.add(open_stammdaten);

        JPanel group_stammdaten = new JPanel();
        group_stammdaten.setLayout(new BoxLayout(group_stammdaten, BoxLayout.X_AXIS));
        group_stammdaten.add(create_importExample);
        group_stammdaten.add(open_stammdaten);
        panel.add(group_stammdaten, gbc);

        // Label Bewegungsdaten hinzufügen
        gbc.gridy = 1; // Zeile
        panel.add(bewegungsdaten, gbc);

        // Bewegungsdaten (Kauf und Wert) hinzufügen
        gbc.gridy = 2; // Zeile

        group_bewegungsdaten.add(create_kauf);
        group_bewegungsdaten.add(create_wert);

        JPanel group_bewegungsdaten = new JPanel();
        group_bewegungsdaten.setLayout(new BoxLayout(group_bewegungsdaten, BoxLayout.X_AXIS));
        group_bewegungsdaten.add(create_kauf);
        group_bewegungsdaten.add(create_wert);
        panel.add(group_bewegungsdaten, gbc);

        // Nächste Runde hinzufügen
        gbc.gridy = 3; // Zeile
        panel.add(create_runde, gbc);

        // Label Anzeige hinzufügen
        gbc.gridy = 4; // Zeile
        panel.add(anzeige, gbc);

        // Spielstand anzeigen hinzufügen
        gbc.gridy = 5; // Zeile
        panel.add(show_spielstand, gbc);

        // Panel dem Frame hinzufügen
        start.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        start.setSize(dim.width / 2, dim.height / 2);
        start.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        start.setVisible(true);
    }

    private void buttonListenerstart() {

        ActionListener importExample = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BeispieldatenImportieren open = new BeispieldatenImportieren();
                open.main();
                start.setVisible(false);
            }
        };
        create_importExample.addActionListener(importExample);

        ActionListener stammdaten = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stammdaten open = new Stammdaten();
                open.main();
                start.setVisible(false);
            }
        };
        open_stammdaten.addActionListener(stammdaten);

        ActionListener kauf = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kauf open = new Kauf();
                open.main();
                start.setVisible(false);
            }
        };
        create_kauf.addActionListener(kauf);

        ActionListener wert = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Wert open = new Wert();
                open.main();
                start.setVisible(false);
            }
        };
        create_wert.addActionListener(wert);

        ActionListener runde = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xxx();
            }
        };
        create_runde.addActionListener(runde);

        ActionListener spielstand = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Spielstand open = new Spielstand();
                open.main();
                start.setVisible(false);
            }
        };
        show_spielstand.addActionListener(spielstand);
    }

    public void main() {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    start();
                    buttonListenerstart();
                }
            });
    }
}
