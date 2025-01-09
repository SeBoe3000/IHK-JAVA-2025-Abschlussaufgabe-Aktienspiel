package Backend;

public class Startkurs {
    private String aktie;
    private Integer startkurs;

    public Startkurs(String aktie, Integer startkurs) {
        this.aktie = aktie;
        this.startkurs = startkurs;
    }

    public Startkurs() {
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
