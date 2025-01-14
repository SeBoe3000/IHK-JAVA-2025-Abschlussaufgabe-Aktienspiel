package Backend;

public class ElementAktienverlauf {
    private Integer runde;
    private String aktie;
    private Integer anzahl;
    private Float kurs;
    private Float kassenbestand;

    public ElementAktienverlauf(Integer runde, String aktie, Integer anzahl, Float kurs, Float kassenbestand) {
        this.runde = runde;
        this.aktie = aktie;
        this.anzahl = anzahl;
        this.kurs = kurs;
        this.kassenbestand = kassenbestand;
    }

    public ElementAktienverlauf() {
    }

    public Integer getRunde() {
        return runde;
    }

    public void setRunde(Integer kurs) {
        this.runde = runde;
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
        // TODO: Prüfung genau 100 einbauen, ggf. auch in GUI Feld anzeigen und nicht bearbeitbar machen
    }

    public Float getKurs() {
        return kurs;
    }

    public void setKurs(Float kurs) {
        this.kurs = kurs;
        // TODO: Prüfung Kurs nicht kleiner 10
    }

    public Float getKassenbestand() {
        return kassenbestand;
    }

    public void setKassenbestand(Float kassenbestand) {
        this.kassenbestand = kassenbestand;
        // TODO: Prüfung zwischen 0 und 100.000 möglich
    }
}
