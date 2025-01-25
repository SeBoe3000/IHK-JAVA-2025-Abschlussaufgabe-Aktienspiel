package Backend;

import Frontend.Komponenten.EingabePanel;
import Frontend.Komponenten.EingabePanelVonBis;

public class Fehler {
    Boolean errorFlag;
    EingabePanel field;
    EingabePanelVonBis fieldVonBis;

    public Fehler(Boolean errorFlag, EingabePanel field) {
        this.errorFlag = errorFlag;
        this.field = field;
    }

    public Fehler(Boolean errorFlag, EingabePanelVonBis fieldVonBis) {
        this.errorFlag = errorFlag;
        this.fieldVonBis = fieldVonBis;
    }

    public EingabePanel getField() {
        return field;
    }

    public EingabePanelVonBis getFieldVonBis() {
        return fieldVonBis;
    }

    public Boolean getErrorFlag() {
        return errorFlag;
    }
}
