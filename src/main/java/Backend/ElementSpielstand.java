package Backend;

public class ElementSpielstand {
    private Integer runde;
    private String gewinnerSpieler;
    private String maxAktienSpieler;
    private String maxAktienUnternehmen;
    private String gewinnUnternehmen;

    public ElementSpielstand(Integer runde, String gewinnerSpieler, String maxAktienSpieler, String maxAktienUnternehmen, String gewinnUnternehmen) {
        this.runde = runde;
        this.gewinnerSpieler = gewinnerSpieler;
        this.maxAktienSpieler = maxAktienSpieler;
        this.maxAktienUnternehmen = maxAktienUnternehmen;
        this.gewinnUnternehmen = gewinnUnternehmen;
    }

    public ElementSpielstand() {
    }

    public Integer getRunde() {
        return runde;
    }

    public void setRunde(Integer runde) {
        this.runde = runde;
    }

    public String getGewinnerSpieler() {
        return gewinnerSpieler;
    }

    public void setGewinnerSpieler(String gewinnerSpieler) {
        this.gewinnerSpieler = gewinnerSpieler;
    }

    public String getMaxAktienSpieler() {
        return maxAktienSpieler;
    }

    public void setMaxAktienSpieler(String maxAktienSpieler) {
        this.maxAktienSpieler = maxAktienSpieler;
    }

    public String getMaxAktienUnternehmen() {
        return maxAktienUnternehmen;
    }

    public void setMaxAktienUnternehmen(String maxAktienUnternehmen) {
        this.maxAktienUnternehmen = maxAktienUnternehmen;
    }

    public String getGewinnUnternehmen() {
        return gewinnUnternehmen;
    }

    public void setGewinnUnternehmen(String gewinnUnternehmen) {
        this.gewinnUnternehmen = gewinnUnternehmen;
    }
}
