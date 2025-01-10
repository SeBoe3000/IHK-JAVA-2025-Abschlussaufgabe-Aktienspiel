package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Person {
    public static final JFrame person = new JFrame("Person erfassen");

    JButton backstart = new JButton("Zurück zu Start");
    JButton backstammdaten = new JButton("Zurück zu Stammdaten");
    ButtonGroup group_back = new ButtonGroup();

    private void person() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Zurück hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        group_back.add(backstart);
        group_back.add(backstammdaten);

        JPanel group_back = new JPanel();
        group_back.setLayout(new BoxLayout(group_back, BoxLayout.X_AXIS));
        group_back.add(backstart);
        group_back.add(backstammdaten);
        panel.add(group_back, gbc);

        // Panel dem Frame hinzufügen
        person.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        person.setSize(dim.width / 2, dim.height / 2);
        person.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        person.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        person.setVisible(true);
    }

    private void buttonListenerstart() {

        ActionListener zurueckStammdaten = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stammdaten.stammdaten.setVisible(true);
                person.setVisible(false);
            }
        };
        backstammdaten.addActionListener(zurueckStammdaten);

        ActionListener zurueckStart = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start.start.setVisible(true);
                person.setVisible(false);
            }
        };
        backstart.addActionListener(zurueckStart);
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                person();
                buttonListenerstart();
            }
        });
    }
}
