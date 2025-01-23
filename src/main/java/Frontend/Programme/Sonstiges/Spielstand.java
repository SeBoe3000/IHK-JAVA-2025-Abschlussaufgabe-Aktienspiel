package Frontend.Programme.Sonstiges;

import Frontend.Cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Spielstand extends JPanel{
    JButton back = new JButton("Zurück zum Start");

    public Spielstand(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // TODO: Abfragen hinzufügen
        /*
        Nachfrage offen wegen welcher Runde herangezogen werden soll.
        Zu jeder Auswertung einbinden, ob es weitere mit dem Ergebnis gibt.

        Reichster Spieler
        SELECT Personid, Kapital
        FROM Kapitalverlauf
        WHERE Runde = (SELECT MAX(Runde) FROM Kapitalverlauf)
        ORDER BY Kapital DESC;

        Spieler mit den meisten Aktien (mit Ausgabe Aktien)
        SELECT PersonID, SUM (Aktienanzahl)
        FROM Transaktionen
        WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen)
        GROUP BY PersonID ORDER BY SUM(Aktienanzahl) DESC LIMIT(1)

        Unternehmen mit den meisten verkauften Aktien
        beim obigen SQL PersonID und AktieISIN tauschen:
        SELECT AktieISIN, SUM (Aktienanzahl)
        FROM Transaktionen
        WHERE Runde = (SELECT MAX(Runde) FROM Transaktionen)
        GROUP BY AktieISIN ORDER BY SUM(Aktienanzahl) DESC LIMIT(1)

        Unternehmen mit dem höchsten Gewinn
        SELECT Aktieisin, Kassenbestand
        FROM Aktienverlauf
        WHERE Runde = (SELECT MAX(Runde) FROM Aktienverlauf)
        ORDER BY Kassenbestand DESC LIMIT(1);
         */

        // Zurück hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(back, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {

        ActionListener zurueck = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Panel wechseln
                Cards.changeCard(Cards.nameStart);
            }
        };
        back.addActionListener(zurueck);
    }
}
