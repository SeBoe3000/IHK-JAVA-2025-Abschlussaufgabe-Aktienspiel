package Frontend.ActionListener.Aktie;

import Frontend.ActionListener.MyAbbrechenListener;
import Frontend.Programme.Stammdaten.Aktie;

public class AktieAbbrechenListener extends MyAbbrechenListener {
    @Override
    protected boolean checkFieldsfilled() {
        Aktie.checkFieldsfilled();
        return false;
    }

    @Override
    protected boolean checkElemetInList() {
        Aktie.checkElemetInList();
        return false;
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
        Aktie.elementHinzu();
        return false;
    }
}
