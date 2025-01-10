package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BeispieldatenImportieren {
    public static final JFrame importExample = new JFrame("Beispieldaten importieren");

    // Checkboxen für Import

    JButton back = new JButton("Zurück zum Start");

    private void beispieldatenImportieren() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Zurück hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(back, gbc);

        // Panel dem Frame hinzufügen
        importExample.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        importExample.setSize(dim.width / 2, dim.height / 2);
        importExample.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        importExample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        importExample.setVisible(true);
    }

    private void buttonListenerstart() {

        ActionListener zurueck = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start.start.setVisible(true);
                importExample.setVisible(false);
            }
        };
        back.addActionListener(zurueck);
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                beispieldatenImportieren();
                buttonListenerstart();
            }
        });
    }
}
