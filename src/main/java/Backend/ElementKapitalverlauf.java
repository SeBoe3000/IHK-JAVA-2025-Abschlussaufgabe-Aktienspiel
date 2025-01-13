package Backend;

public class ElementKapitalverlauf {
    private Integer runde;
    private Integer person;
    private Float betrag;

    public ElementKapitalverlauf(Integer runde, Integer person, Float betrag) {
        this.runde = runde;
        this.person = person;
        this.betrag = betrag;
    }

    public ElementKapitalverlauf() {
    }

    public Integer getRunde() {
        return runde;
    }

    public void setRunde(Integer runde) {
        runde = runde;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        person = person;
    }

    public Float getBetrag() {
        return betrag;
    }

    public void setBetrag(Float betrag) {
        betrag = betrag;
    }
}
