package Backend;

public class Startkapital {
    private String person;
    private Float betrag;

    public Startkapital(String person, Float betrag) {
        this.person = person;
        this.betrag = betrag;
    }

    public Startkapital() {
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        person = person;
    }

    public Float getBetrag() {
        return betrag;
    }

    public void setBetrag(Float betrag) {
        betrag = betrag;
    }
}
