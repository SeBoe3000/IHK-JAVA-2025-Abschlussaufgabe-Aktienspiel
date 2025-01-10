package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stammdaten {
    public static final JFrame stammdaten = new JFrame("Stammdaten");

    JButton create_person = new JButton("Personen erfassen");
    JButton create_aktie = new JButton("Aktien erfassen");
    ButtonGroup group_stammdaten = new ButtonGroup();

    JButton create_startkapital = new JButton("Startkapital Personen erfassen");
    JButton create_startkurs = new JButton("Startkurs Aktien erfassen");
    ButtonGroup group_startdaten = new ButtonGroup();

    JButton back = new JButton("Zurück zum Start");

    private void stammdaten() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Stammdaten (Person und Aktie erfassen) hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        group_stammdaten.add(create_person);
        group_stammdaten.add(create_aktie);

        JPanel group_stammdaten = new JPanel();
        group_stammdaten.setLayout(new BoxLayout(group_stammdaten, BoxLayout.X_AXIS));
        group_stammdaten.add(create_person);
        group_stammdaten.add(create_aktie);
        panel.add(group_stammdaten, gbc);

        // Startdaten (Startkapital und Startkurs) hinzufügen
        gbc.gridy = 1; // Zeile

        group_startdaten.add(create_startkapital);
        group_startdaten.add(create_startkurs);

        JPanel group_startdaten = new JPanel();
        group_startdaten.setLayout(new BoxLayout(group_startdaten, BoxLayout.X_AXIS));
        group_startdaten.add(create_startkapital);
        group_startdaten.add(create_startkurs);
        panel.add(group_startdaten, gbc);

        // Zurück hinzufügen
        gbc.gridy = 2; // Zeile
        panel.add(back, gbc);

        // Panel dem Frame hinzufügen
        stammdaten.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        stammdaten.setSize(dim.width / 2, dim.height / 2);
        stammdaten.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        stammdaten.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        stammdaten.setVisible(true);
    }

    private void buttonListenerstart() {


        ActionListener person = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person open = new Person();
                open.main();
                stammdaten.setVisible(false);
            }
        };
        create_person.addActionListener(person);

        ActionListener aktie = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Aktie open = new Aktie();
                open.main();
                stammdaten.setVisible(false);
            }
        };
        create_aktie.addActionListener(aktie);

        ActionListener startkapital = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Startkapital open = new Startkapital();
                open.main();
                stammdaten.setVisible(false);
            }
        };
        create_startkapital.addActionListener(startkapital);

        ActionListener startkurs = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Startkurs open = new Startkurs();
                open.main();
                stammdaten.setVisible(false);
            }
        };
        create_startkurs.addActionListener(startkurs);

        ActionListener zurueck = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start.start.setVisible(true);
                stammdaten.setVisible(false);
            }
        };
        back.addActionListener(zurueck);
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                stammdaten();
                buttonListenerstart();
            }
        });
    }
}
