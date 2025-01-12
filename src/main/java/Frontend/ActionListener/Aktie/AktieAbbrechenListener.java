package Frontend.ActionListener.Aktie;

import Frontend.ActionListener.Checks;
import Frontend.ActionListener.MyAbbrechenListener;
import Frontend.Programme.Stammdaten.Aktie;

public class AktieAbbrechenListener extends MyAbbrechenListener {

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
