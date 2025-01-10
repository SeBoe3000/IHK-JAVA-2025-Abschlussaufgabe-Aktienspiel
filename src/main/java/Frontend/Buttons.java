package Frontend;

import javax.swing.*;

public class Buttons extends JPanel {
    private JPanel panel = new JPanel();
    public JButton create_btn = new JButton("Erfassen");
    public JButton ok_btn = new JButton("OK");
    public JButton cancel_btn = new JButton("Abbrechen");
    public JButton backstart = new JButton("Zurück zu Start");
    ButtonGroup transaction_group = new ButtonGroup();

    public Buttons(){
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(create_btn);
        panel.add(ok_btn);
        panel.add(cancel_btn);
        panel.add(backstart);
        this.add(panel);
    }
    public String getButton(){
        return getButton();
    }

    // Methode zum Verschwinden von "Zurück zum Start"
    public void setVisibleFalse(){
        backstart.setVisible(false);
    }
}

