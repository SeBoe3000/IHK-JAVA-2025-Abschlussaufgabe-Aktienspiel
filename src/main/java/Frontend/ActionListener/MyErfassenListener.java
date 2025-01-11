package Frontend.ActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class MyErfassenListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        elementHinzu();
    }

    protected abstract boolean elementHinzu();
}
