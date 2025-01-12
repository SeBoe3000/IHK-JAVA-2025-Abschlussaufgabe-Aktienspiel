package Frontend.ActionListener;

import Frontend.Programme.Stammdaten.Aktie;

import javax.swing.*;

public class AktienListener extends MyActionListener {
    public AktienListener(JButton Btn) {
        super(Btn);
    }

    @Override
    protected boolean checkFieldsfilled() {
        Boolean action = Aktie.checkFieldsfilled();
        return action;
    }

    @Override
    protected boolean checkElemetInList() {
        Boolean action = Checks.checkElementInList(Aktie.AktieList);
        return action;
    }

    @Override
    protected void elementInsert() {
        Aktie.elementInsert();
    }

    @Override
    protected void backToStart() {
        Aktie.backToStart();
    }

    @Override
    protected boolean elementHinzu() {
        Boolean action = Aktie.elementHinzu();
        return action;
    }
}
