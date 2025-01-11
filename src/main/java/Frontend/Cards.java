package Frontend;

import Frontend.Programme.Bewegungsdaten.Kauf;
import Frontend.Programme.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cards {
    public static final JFrame start = new JFrame("Aktienspiel");

    private void start() {
        // CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Panels erstellen
        JPanel panelStart = new Start(cardLayout, cardPanel);
        JPanel panelBeispieldatenImportieren = new BewegungsdatenPanel(cardLayout, cardPanel);
        JPanel panelStammdaten = new AnzeigePanel(cardLayout, cardPanel);
        JPanel panelAktie = new AnzeigePanel(cardLayout, cardPanel);
        JPanel panelPerson = new AnzeigePanel(cardLayout, cardPanel);
        JPanel panelStartkapital = new AnzeigePanel(cardLayout, cardPanel);
        JPanel panelStartkurs = new AnzeigePanel(cardLayout, cardPanel);
        JPanel panelKauf = new Kauf(cardLayout, cardPanel);
        JPanel panelWert = new AnzeigePanel(cardLayout, cardPanel);
        JPanel panelSpielstand = new AnzeigePanel(cardLayout, cardPanel);

        // Panels CardLayout hinzufügen
        cardPanel.add(panelStart, "panelStart");
        cardPanel.add(panelBeispieldatenImportieren, "panelBeispieldatenImportieren");
        cardPanel.add(panelStammdaten, "panelStammdaten");
        cardPanel.add(panelAktie, "panelAktie");
        cardPanel.add(panelPerson, "panelPerson");
        cardPanel.add(panelStartkapital, "panelStartkapital");
        cardPanel.add(panelStartkurs, "panelStartkurs");
        cardPanel.add(panelKauf, "panelKauf");
        cardPanel.add(panelWert, "panelWert");
        cardPanel.add(panelSpielstand, "panelSpielstand");

        // Panel dem Frame hinzufügen
        start.add(cardPanel);

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

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                start();
            //buttonListenerstart();
            }
        });
    }


}


class StammdatenPanel extends JPanel {
    public StammdatenPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Stammdaten");
        add(label);

        JButton button1 = new JButton("Weiter zu Bewegungsdaten");
        add(button1);

        // ActionListener für den Button
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "panelStart"); // Wechselt zu Panel 2
            }
        });
    }
}

// BewegungsdatenPanel - Panel 2
class BewegungsdatenPanel extends JPanel {
    public BewegungsdatenPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Bewegungsdaten");
        add(label);

        JButton button2 = new JButton("Weiter zu Anzeige");
        add(button2);

        // ActionListener für den Button
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "panelStart"); // Wechselt zu Panel 3
            }
        });
    }
}

// AnzeigePanel - Panel 3
class AnzeigePanel extends JPanel {
    public AnzeigePanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Anzeige");
        add(label);

        JButton button3 = new JButton("Zurück zu Stammdaten");
        add(button3);

        // ActionListener für den Button
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "panelStart"); // Wechselt zu Panel 1
            }
        });
    }
}
