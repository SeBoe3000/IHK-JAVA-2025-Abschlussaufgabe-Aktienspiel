package Frontend;

import Frontend.Programme.Anzeige.Spielstand;
import Frontend.Programme.Bewegungsdaten.Kauf;
import Frontend.Programme.Bewegungsdaten.Wert;
import Frontend.Programme.Konfiguration.*;
import Frontend.Programme.Stammdaten.*;
import Frontend.Programme.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Cards implements KeyListener {
    public static final JFrame start = new JFrame("Aktienspiel");
    // CardLayout
    public static final CardLayout cardLayout = new CardLayout();
    public static final JPanel cardPanel = new JPanel(cardLayout);

    // Name Cards als String für späteren Zugriff
    public static String nameStart = "Start";
    public static String nameBeispieldatenImportieren = "Beispieldaten importieren";
    public static String nameStammdaten = "Stammdaten erfassen";
    public static String nameAktie = "Aktien erfassen";
    public static String namePerson = "Personen erfassen";
    public static String nameStartkapital = "Startkapital erfassen";
    public static String nameStartkurs = "Startkurse erfassen";
    public static String nameEinstellungen = "Einstellungen erfassen";
    public static String nameEinstellungenAktien = "Einstellung - Aktien";
    public static String nameEinstellungenPersonen = "Einstellung - Personen";
    public static String nameEinstellungenAktienverlauf = "Einstellung - Aktienverlauf";
    public static String nameEinstellungenTransaktionen = "Einstellung - Transaktionen";
    public static String nameKauf = "Kauf";
    public static String nameWert = "Wert";
    public static String nameSpielstand = "Spielstand";

    // Für die Anzeige der Hilfe
    public static String currentCard = nameStart;

    // Fensterüberschrift
    private static JLabel headline = new JLabel("Aktienspiel", SwingConstants.LEFT);

    private void start() {
        // Panels erstellen
        JPanel panelStart = new Start(cardLayout, cardPanel);
        JPanel panelBeispieldatenImportieren = new BeispieldatenImportieren(cardLayout, cardPanel);
        JPanel panelStammdaten = new Stammdaten(cardLayout, cardPanel);
        JPanel panelAktie = new Aktie(cardLayout, cardPanel);
        JPanel panelPerson = new Person(cardLayout, cardPanel);
        JPanel panelStartkapital = new Startkapital(cardLayout, cardPanel);
        JPanel panelStartkurs = new Startkurs(cardLayout, cardPanel);
        JPanel panelEinstellungen = new Einstellungen(cardLayout, cardPanel);
        JPanel panelEinstellungenAktien = new EinstellungenAktien(cardLayout, cardPanel);
        JPanel panelEinstellungenPersonen = new EinstellungenPersonen(cardLayout, cardPanel);
        JPanel panelEinstellungenAktienverlauf = new EinstellungenAktienverlauf(cardLayout, cardPanel);
        JPanel panelEinstellungenTransaktionen = new EinstellungenTransaktionen(cardLayout, cardPanel);
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
        cardPanel.add(panelEinstellungen, nameEinstellungen);
        cardPanel.add(panelEinstellungenAktien, nameEinstellungenAktien);
        cardPanel.add(panelEinstellungenPersonen, nameEinstellungenPersonen);
        cardPanel.add(panelEinstellungenAktienverlauf, nameEinstellungenAktienverlauf);
        cardPanel.add(panelEinstellungenTransaktionen, nameEinstellungenTransaktionen);
        cardPanel.add(panelKauf, nameKauf);
        cardPanel.add(panelWert, nameWert);
        cardPanel.add(panelSpielstand, nameSpielstand);

        // Panel dem Frame hinzufügen
        start.add(cardPanel);
        start.add(headline, BorderLayout.NORTH);  // Header hinzufügen
        start.add(cardPanel, BorderLayout.CENTER);  // Cards Panel hinzufügen

        // KeyListener
        start.addKeyListener(this);
        start.setFocusable(true);

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

    // Card wechseln und Fensterüberschrift ändern
    public static void changeCard(String card) {
        cardLayout.show(cardPanel, card);
        headline.setText(card);
        currentCard = card; // Speichern der aktuellen Karte für das Öffnen der Hilfe
    }

    public void main() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Bei F1 die jeweilige Hilfe aufrufen
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            try {
                URI uri = null;
                if (nameStart.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/l/cp/tcuWsoAC");
                } else if (nameBeispieldatenImportieren.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/l4AG");
                } else if (nameStammdaten .equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/tAEB");
                } else if (nameAktie.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/FwAL");
                } else if (namePerson.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/AQAL");
                } else if (nameStartkapital.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/WYAF");
                } else if (nameStartkurs.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/CgEC");
                } else if (nameEinstellungen.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/_wAC");
                } else if (nameEinstellungenAktien.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/UAAH");
                } else if (nameEinstellungenPersonen.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/SQIB");
                } else if (nameEinstellungenAktienverlauf.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/mgAH");
                } else if (nameEinstellungenTransaktionen.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/ZgAL");
                } else if (nameKauf.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/kAIB");
                } else if (nameWert.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/cwAL");
                } else if (nameSpielstand.equals(currentCard)) {
                    uri = new URI("https://docush.atlassian.net/wiki/x/iQEC");
                }
                Desktop.getDesktop().browse(uri);
            } catch (IOException e1){
                e1.printStackTrace();
            } catch (URISyntaxException e2) {
                e2.printStackTrace();
            }
        }
    }
}
