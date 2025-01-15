package Frontend.Komponenten;

import javax.swing.*;
import java.awt.*;

public class EingabePanelVonBis extends JPanel {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JLabel labelvon = new JLabel(" Von ");
    private JTextField textFieldVon = new JTextField();
    private JLabel labelbis = new JLabel(" Bis ");
    private JTextField textFieldBis = new JTextField();
    ButtonGroup group = new ButtonGroup();

    public EingabePanelVonBis(String labelText){
        label.setText(labelText);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(label);
        panel.add(labelvon);
        panel.add(textFieldVon);
        panel.add(labelbis);
        panel.add(textFieldBis);
        //textField.setMinimumSize(new Dimension(200,50));
        textFieldVon.setPreferredSize(new Dimension(90,50));
        textFieldBis.setPreferredSize(new Dimension(90,50));
        this.add(panel);
    }
    public String getTextfieldVon(){
        return textFieldVon.getText();
    }

    public void setTextFieldVon(String text) {
        textFieldVon.setText(text);
    }

    public String getTextfieldBis(){
        return textFieldVon.getText();
    }

    public void setTextFieldBis(String text) {
        textFieldBis.setText(text);
    }

    // Methoden zum Schriftfarbe Eingabefeld auf rot setzen und zurück bei einem Fehler:
    public void setErrorVon(){
        textFieldVon.setForeground(Color.RED);
    }

    public void removeErrorVon(){
        textFieldVon.setForeground(Color.BLACK);
    }

    public void setErrorBis(){
        textFieldBis.setForeground(Color.RED);
    }

    public void removeErrorBis(){
        textFieldBis.setForeground(Color.BLACK);
    }

    // Methoden zum möglichen ausgrauen
    public void setEnabledTrueVon(){
        textFieldVon.setEnabled(true);
    }

    public void setEnabledFalseVon(){
        textFieldVon.setEnabled(false);
    }

    public void setEnabledTrueBis(){
        textFieldBis.setEnabled(true);
    }

    public void setEnabledFalseBis(){
        textFieldBis.setEnabled(false);
    }
}
