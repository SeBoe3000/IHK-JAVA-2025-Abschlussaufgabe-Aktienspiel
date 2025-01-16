package Frontend.Programme;

import Frontend.Cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start extends JPanel {
    // Zum Zählen der Runden
    public static Integer runde = 1;

    JButton create_importExample = new JButton("Beispieldaten importieren");
    JButton open_stammdaten = new JButton("Stammdaten erfassen");
    JButton open_einstellung = new JButton("Einstellungen");
    ButtonGroup group_stammdaten = new ButtonGroup();

    JLabel bewegungsdaten = new JLabel("Bewegungsdaten");
    JButton create_kauf = new JButton("Käufe erfassen");
    JButton create_wert = new JButton("Unternehmenswerte erfassen");

    ButtonGroup group_bewegungsdaten = new ButtonGroup();
    JButton create_runde = new JButton("Nächste Runde");

    JLabel anzeige = new JLabel("Anzeige");
    JButton show_spielstand = new JButton("Spielstand anzeigen");

    public Start(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Stammdaten hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        group_stammdaten.add(create_importExample);
        group_stammdaten.add(open_stammdaten);
        group_stammdaten.add(open_einstellung);

        JPanel group_stammdaten = new JPanel();
        group_stammdaten.setLayout(new BoxLayout(group_stammdaten, BoxLayout.X_AXIS));
        group_stammdaten.add(create_importExample);
        group_stammdaten.add(open_stammdaten);
        group_stammdaten.add(open_einstellung);
        add(group_stammdaten, gbc);

        // Label Bewegungsdaten hinzufügen
        gbc.gridy = 1; // Zeile
        add(bewegungsdaten, gbc);

        // Bewegungsdaten (Kauf und Wert) hinzufügen
        gbc.gridy = 2; // Zeile

        group_bewegungsdaten.add(create_kauf);
        group_bewegungsdaten.add(create_wert);

        JPanel group_bewegungsdaten = new JPanel();
        group_bewegungsdaten.setLayout(new BoxLayout(group_bewegungsdaten, BoxLayout.X_AXIS));
        group_bewegungsdaten.add(create_kauf);
        group_bewegungsdaten.add(create_wert);
        add(group_bewegungsdaten, gbc);

        // Nächste Runde hinzufügen
        gbc.gridy = 3; // Zeile
        add(create_runde, gbc);

        // Label Anzeige hinzufügen
        gbc.gridy = 4; // Zeile
        add(anzeige, gbc);

        // Spielstand anzeigen hinzufügen
        gbc.gridy = 5; // Zeile
        add(show_spielstand, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {

        ActionListener importExample = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameBeispieldatenImportieren);
            }
        };
        create_importExample.addActionListener(importExample);

        ActionListener stammdaten = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameStammdaten);
            }
        };
        open_stammdaten.addActionListener(stammdaten);

        ActionListener einstellung = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameEinstellungen);
            }
        };
        open_einstellung.addActionListener(einstellung);

        ActionListener kauf = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameKauf);
            }
        };
        create_kauf.addActionListener(kauf);

        ActionListener wert = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameWert);
            }
        };
        create_wert.addActionListener(wert);

        ActionListener runde = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: aktuelle Runde Kapitalverlauf und Aktienverlauf ermitteln per SQL
                // TODO: Runde Kapitalverkauf = 0 --> Fehlermeldung Startkapital erfassen
                // TODO: Runde Aktienverlauf = 0 --> Fehlermeldung Startkurs erfassen
                // TODO: Runde Kapitalverlauf < Aktienverlauf --> Fehlermeldung Unternehmenswerte erfassen
                // TODO: Runde Kapitalverlauf > Aktienverlauf --> Fehlermeldung Käufe erfassen
                // TODO: Runde Kapitalverlauf = Aktienverlauf dann:
                    // TODO: Prüfung in aktueller Runde wurden 3 bis 14 Aktien bzw. Personen angesprochen
                    // TODO: Prüfung zu jeder gekauften Aktie liegt ein Aktienkurs und Kassenbestand vor
                    // TODO: Sofern die beiden Prüfungen erfolgreich: Dividende berechnen
                        // TODO: Pro verwendeter Aktie die Personen nach Aktien sortieren (größter Wert oben)
                        // TODO: Prüfung: wie oft kommt die Aktienanzahl vom ersten Platz vor?
                            // TODO: bei mehr als 1 mal
                                // TODO: 40% vom Kassenbestand durch die Anzahl teilen
                                // TODO: den Personen als Dividende einfügen
                            // TODO: bei genau einem
                                // TODO: 30% vom Kassenbestand der Person als Dividende einfügen
                                // TODO: Prüfung: wie oft kommt die Aktienanzahl vom zweiten Platz vor?
                                    // TODO: bei mehr als 1 mal
                                        // TODO: 10% vom Kassenbestand durch die Anzahl teilen
                                        // TODO: den Personen als Dividende einfügen
                                    // TODO: bei genau einem
                                        // TODO: 10% vom Kassenbestand der Person als Dividende einfügen
                    // TODO: Runde erhöhen
                    // TODO: Meldung über erfolgreich gespielte Runde bringen
                    // TODO: Abfragen für Spielstand ggf. aktualisieren

            }
        };
        create_runde.addActionListener(runde);

        ActionListener spielstand = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameSpielstand);
            }
        };
        show_spielstand.addActionListener(spielstand);
    }
}