package Frontend.Komponenten;

import javax.swing.*;
import java.awt.*;

public class EingabePanel extends JPanel {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JTextField textField = new JTextField();
    ButtonGroup group = new ButtonGroup();

    public EingabePanel(String labelText){
        label.setText(labelText);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(label);
        panel.add(textField);
        //textField.setMinimumSize(new Dimension(200,50));
        textField.setPreferredSize(new Dimension(200,50));
        this.add(panel);
    }
    public String getTextfield(){
        return textField.getText();
    }

    public void setTextField(String text) {
        textField.setText(text);
    }

    // Methoden zum Schriftfarbe Eingabefeld auf rot setzen und zurück bei einem Fehler:
    public void setError(){
        textField.setForeground(Color.RED);
    }

    public void removeError(){
        textField.setForeground(Color.BLACK);
    }

    // Methoden zum möglichen ausgrauen
    public void setEnabledTrue(){
        textField.setEnabled(true);
    }

    public void setEnabledFalse(){
        textField.setEnabled(false);
    }

    // Möglichkeit die Feldgröße nachträglich zu ändern
    public void setPreferredSize(int width, int height) {
        textField.setPreferredSize(new Dimension(width,height));
    }
}
