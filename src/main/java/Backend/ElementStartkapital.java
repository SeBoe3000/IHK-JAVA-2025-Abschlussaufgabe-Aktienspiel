package Backend;

public class ElementStartkapital {
    private String person;
    private Float betrag;

    public ElementStartkapital(String person, Float betrag) {
        this.person = person;
        this.betrag = betrag;
    }

    public ElementStartkapital() {
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
