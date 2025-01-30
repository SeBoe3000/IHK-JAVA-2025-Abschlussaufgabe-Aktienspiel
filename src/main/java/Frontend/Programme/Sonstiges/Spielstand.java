package Frontend.Programme.Sonstiges;

import Datenbank.SQLSpielstand;
import Frontend.Cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Spielstand extends JPanel{
    JButton back = new JButton("Zurück zum Start");

    // Ergebnisse von Abfragen
    static JLabel abfrage1 = new JLabel();
    static JLabel abfrage2 = new JLabel();
    static JLabel abfrage3 = new JLabel();
    public static JLabel abfrage4 = new JLabel();

    public Spielstand(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Abfragen hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        add(abfrage1, gbc);

        gbc.gridy = 1; // Zeile
        add(abfrage2, gbc);

        gbc.gridy = 2; // Zeile
        add(abfrage3, gbc);

        gbc.gridy = 3; // Zeile
        add(abfrage4, gbc);

        // Zurück hinzufügen
        gbc.gridy = 4; // Zeile
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

    public static void spielstandAktualisieren(){
        abfrage1.setText(SQLSpielstand.getOneString("SELECT GewinnerSpieler FROM Spielstand " +
                "WHERE Runde = (SELECT MAX(Runde) FROM Spielstand)"));
        abfrage2.setText(SQLSpielstand.getOneString("SELECT MaxAktienSpieler FROM Spielstand " +
                "WHERE Runde = (SELECT MAX(Runde) FROM Spielstand)"));
        abfrage3.setText(SQLSpielstand.getOneString("SELECT MaxAktienUnternehmen FROM Spielstand " +
                "WHERE Runde = (SELECT MAX(Runde) FROM Spielstand)"));
        abfrage4.setText(SQLSpielstand.getOneString("SELECT GewinnUnternehmen FROM Spielstand " +
                "WHERE Runde = (SELECT MAX(Runde) FROM Spielstand)"));
    }
}
