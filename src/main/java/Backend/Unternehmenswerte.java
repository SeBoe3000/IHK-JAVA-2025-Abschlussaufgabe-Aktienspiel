package Backend;

public class Unternehmenswerte {
    private String aktie;
    private Float kurs;
    private Float kassenbestand;

    public Unternehmenswerte(String aktie, Float kurs, Float kassenbestand) {
        this.aktie = aktie;
        this.kurs = kurs;
        this.kassenbestand = kassenbestand;
    }

    public Unternehmenswerte() {
    }

    public String getAktie() {
        return aktie;
    }

    public void setAktie(String aktie) {
        this.aktie = aktie;
    }

    public Float getKurs() {
        return kurs;
    }

    public void setKurs(Float kurs) {
        this.kurs = kurs;
    }

    public Float getKassenbestand() {
        return kassenbestand;
    }

    public void setKassenbestand(Float kassenbestand) {
        this.kassenbestand = kassenbestand;
    }
}
