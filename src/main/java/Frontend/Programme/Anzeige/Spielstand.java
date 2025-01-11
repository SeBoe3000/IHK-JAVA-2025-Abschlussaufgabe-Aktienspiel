package Frontend.Programme.Anzeige;

import Frontend.Cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Frontend.Cards.cardLayout;

public class Spielstand extends JPanel{
    JButton back = new JButton("Zurück zum Start");

    public Spielstand(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

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
                cardLayout.show(Cards.cardPanel, "panelStart");
            }
        };
        back.addActionListener(zurueck);
    }
}
