package Backend;

public class ElementStartkurs {
    private String aktie;
    private Integer startkurs;

    public ElementStartkurs(String aktie, Integer startkurs) {
        this.aktie = aktie;
        this.startkurs = startkurs;
    }

    public ElementStartkurs() {
    }

    public String getAktie() {
        return aktie;
    }

    public void setAktie(String aktie) {
        this.aktie = aktie;
    }

    public Integer getStartkurs() {
        return startkurs;
    }

    public void setStartkurs(Integer startkurs) {
        this.startkurs = startkurs;
    }
}
