package Backend;

public class ElementAktie {
    private String isin;
    private String name;

    public ElementAktie(String isin, String name) {
        this.isin = isin;
        this.name = name;
    }

    public ElementAktie() {
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
