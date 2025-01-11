package Frontend;

import Frontend.Programme.Anzeige.Spielstand;
import Frontend.Programme.Bewegungsdaten.Kauf;
import Frontend.Programme.Bewegungsdaten.Wert;
import Frontend.Programme.Stammdaten.*;
import Frontend.Programme.Start;

import javax.swing.*;
import java.awt.*;

public class Cards {
    public static final JFrame start = new JFrame("Aktienspiel");
    // CardLayout
    public static final CardLayout cardLayout = new CardLayout();
    public static final JPanel cardPanel = new JPanel(cardLayout);

    private void start() {
        // Panels erstellen
        JPanel panelStart = new Start(cardLayout, cardPanel);
        JPanel panelBeispieldatenImportieren = new BeispieldatenImportieren(cardLayout, cardPanel);
        JPanel panelStammdaten = new Stammdaten(cardLayout, cardPanel);
        JPanel panelAktie = new Aktie(cardLayout, cardPanel);
        JPanel panelPerson = new Person(cardLayout, cardPanel);
        JPanel panelStartkapital = new Startkapital(cardLayout, cardPanel);
        JPanel panelStartkurs = new Startkurs(cardLayout, cardPanel);
        JPanel panelKauf = new Kauf(cardLayout, cardPanel);
        JPanel panelWert = new Wert(cardLayout, cardPanel);
        JPanel panelSpielstand = new Spielstand(cardLayout, cardPanel);

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
            }
        });
    }
}
