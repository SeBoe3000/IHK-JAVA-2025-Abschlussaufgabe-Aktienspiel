package Backend;

public class ElementTransaktionen {
    private String person;
    private String aktie;
    private Integer anzahl;

    public ElementTransaktionen(String person, String aktie, Integer anzahl) {
        this.person = person;
        this.aktie = aktie;
        this.anzahl = anzahl;
    }

    public ElementTransaktionen() {
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getAktie() {
        return aktie;
    }

    public void setAktie(String aktie) {
        this.aktie = aktie;
    }

    public Integer getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Integer anzahl) {
        this.anzahl = anzahl;
    }
}
