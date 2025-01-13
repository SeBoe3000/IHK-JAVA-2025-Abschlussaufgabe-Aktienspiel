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

    // Name Cards als String für späteren Zugriff
    public static String nameStart = "Start";
    public static String nameBeispieldatenImportieren = "BeispieldatenImportieren";
    public static String nameStammdaten = "Stammdaten";
    public static String nameAktie = "Aktie";
    public static String namePerson = "Person";
    public static String nameStartkapital = "Startkapital";
    public static String nameStartkurs = "Startkurs";
    public static String nameKauf = "Kauf";
    public static String nameWert = "Wert";
    public static String nameSpielstand = "Spielstand";

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
        cardPanel.add(panelStart, nameStart);
        cardPanel.add(panelBeispieldatenImportieren, nameBeispieldatenImportieren);
        cardPanel.add(panelStammdaten, nameStammdaten);
        cardPanel.add(panelAktie, nameAktie);
        cardPanel.add(panelPerson, namePerson);
        cardPanel.add(panelStartkapital, nameStartkapital);
        cardPanel.add(panelStartkurs, nameStartkurs);
        cardPanel.add(panelKauf, nameKauf);
        cardPanel.add(panelWert, nameWert);
        cardPanel.add(panelSpielstand, nameSpielstand);

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
