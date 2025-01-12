package Frontend.ActionListener.Aktie;

import Frontend.ActionListener.MyErfassenListener;
import Frontend.Programme.Stammdaten.Aktie;

public class AktieErfassenListener extends MyErfassenListener {
    @Override
    protected boolean elementHinzu() {
        Boolean action = Aktie.elementHinzu();
        return action;
    }
}
