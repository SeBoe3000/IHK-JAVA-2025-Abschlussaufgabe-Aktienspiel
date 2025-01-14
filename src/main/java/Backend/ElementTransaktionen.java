package Backend;

public class ElementTransaktionen {
    private Integer runde;
    private Integer person;
    private String aktie;
    private Integer anzahl;
    private Float dividende;

    public ElementTransaktionen(Integer runde, Integer person, String aktie, Integer anzahl, Float dividende) {
        this.runde = runde;
        this.person = person;
        this.aktie = aktie;
        this.anzahl = anzahl;
        this.dividende = dividende;
    }

    public ElementTransaktionen() {
    }

    public Integer getRunde() {
        return runde;
    }

    public void setRunde(Integer anzahl) {
        this.runde = runde;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
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

    public Float getDividende() {
        return dividende;
    }

    public void setDividende(Float anzahl) {
        this.dividende = dividende;
    }
}
