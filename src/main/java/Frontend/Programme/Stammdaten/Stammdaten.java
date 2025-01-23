package Frontend.Programme.Stammdaten;

import Frontend.ActionListenerUpdate.EinstellungenPersonenListener;
import Frontend.Cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stammdaten extends JPanel{
    JButton create_person = new JButton("Personen erfassen");
    JButton create_aktie = new JButton("Aktien erfassen");
    ButtonGroup group_stammdaten = new ButtonGroup();

    JButton create_startkapital = new JButton("Startkapital Personen erfassen");
    JButton create_startkurs = new JButton("Startkurs Aktien erfassen");
    ButtonGroup group_startdaten = new ButtonGroup();

    JButton back = new JButton("Zurück zum Start");

    public Stammdaten(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

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
        add(group_stammdaten, gbc);

        // Startdaten (Startkapital und Startkurs) hinzufügen
        gbc.gridy = 1; // Zeile

        group_startdaten.add(create_startkapital);
        group_startdaten.add(create_startkurs);

        JPanel group_startdaten = new JPanel();
        group_startdaten.setLayout(new BoxLayout(group_startdaten, BoxLayout.X_AXIS));
        group_startdaten.add(create_startkapital);
        group_startdaten.add(create_startkurs);
        add(group_startdaten, gbc);

        // Zurück hinzufügen
        gbc.gridy = 2; // Zeile
        add(back, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {

        ActionListener person = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.namePerson);
            }
        };
        create_person.addActionListener(person);

        ActionListener aktie = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameAktie);
            }
        };
        create_aktie.addActionListener(aktie);

        ActionListener startkapital = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameStartkapital);

                // Spartkapital befüllen und Sperren, wenn nicht bearbeitbar
                System.out.println("Einstellung: " + EinstellungenPersonenListener.getEinstellungString("defaultStrartkapitalBearbeitbar"));
                if (EinstellungenPersonenListener.getEinstellungString("defaultStrartkapitalBearbeitbar").equals("N")) {
                    Startkapital.betrag.setTextField(String.valueOf(EinstellungenPersonenListener.getEinstellungFloat("defaultStrartkapital")));
                    Startkapital.betrag.setEnabledFalse();
                } else {
                    Startkapital.betrag.setEnabledTrue();
                }
            }
        };
        create_startkapital.addActionListener(startkapital);

        ActionListener startkurs = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameStartkurs);
            }
        };
        create_startkurs.addActionListener(startkurs);

        ActionListener zurueck = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameStart);
            }
        };
        back.addActionListener(zurueck);
    }
}
