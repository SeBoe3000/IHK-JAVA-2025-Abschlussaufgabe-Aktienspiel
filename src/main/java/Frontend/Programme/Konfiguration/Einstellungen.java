package Frontend.Programme.Konfiguration;

import Frontend.ActionListenerUpdate.EinstellungenAktienListener;
import Frontend.ActionListenerUpdate.EinstellungenAktienverlaufListener;
import Frontend.ActionListenerUpdate.EinstellungenPersonenListener;
import Frontend.ActionListenerUpdate.EinstellungenTransaktionenListener;
import Frontend.Cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Einstellungen extends JPanel {
    JButton einstellung_aktie = new JButton("Aktien");
    JButton einstellung_person = new JButton("Person");
    ButtonGroup group_one = new ButtonGroup();
    JButton einstellung_aktienverlauf = new JButton("Aktienverlauf");
    JButton einstellung_transaktionen = new JButton("Transaktionen");
    ButtonGroup group_two = new ButtonGroup();

    JButton back = new JButton("Zurück zum Start");

    public Einstellungen(CardLayout cardLayout, JPanel cardPanel) {
        // GridBagLayout direkt auf Panel verwenden
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Einstellungen (Person und Aktie erfassen) hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        group_one.add(einstellung_aktie);
        group_one.add(einstellung_person);

        JPanel group_one = new JPanel();
        group_one.setLayout(new BoxLayout(group_one, BoxLayout.X_AXIS));
        group_one.add(einstellung_aktie);
        group_one.add(einstellung_person);
        add(group_one, gbc);

        // Einstellungen (Aktienverlauf und Transaktionen) hinzufügen
        gbc.gridy = 1; // Zeile

        group_two.add(einstellung_aktienverlauf);
        group_two.add(einstellung_transaktionen);

        JPanel group_two = new JPanel();
        group_two.setLayout(new BoxLayout(group_two, BoxLayout.X_AXIS));
        group_two.add(einstellung_aktienverlauf);
        group_two.add(einstellung_transaktionen);
        add(group_two, gbc);

        // Zurück hinzufügen
        gbc.gridy = 2; // Zeile
        add(back, gbc);

        // ActionListener hinzufügen
        buttonListener();
    }

    private void buttonListener() {
        ActionListener aktie = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameEinstellungenAktien);
                EinstellungenAktienListener.setDefaults();
            }
        };
        einstellung_aktie.addActionListener(aktie);

        ActionListener person = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameEinstellungenPersonen);
                EinstellungenPersonenListener.setDefaults();
            }
        };
        einstellung_person.addActionListener(person);

        ActionListener aktienverlauf = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameEinstellungenAktienverlauf);
                EinstellungenAktienverlaufListener.setDefaults();
            }
        };
        einstellung_aktienverlauf.addActionListener(aktienverlauf);

        ActionListener transaktion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameEinstellungenTransaktionen);
                EinstellungenTransaktionenListener.setDefaults();
            }
        };
        einstellung_transaktionen.addActionListener(transaktion);

        ActionListener zurueck = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cards.changeCard(Cards.nameStart);
            }
        };
        back.addActionListener(zurueck);
    }
}
