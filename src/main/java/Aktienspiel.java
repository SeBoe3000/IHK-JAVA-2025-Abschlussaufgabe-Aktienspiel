import Datenbank.Tabellen;
import Frontend.Cards;

public class Aktienspiel {
    public static void main(String[] args) {
        // Tabellen erstellen, falls noch nicht vorhanden
        Tabellen.createTableall();
        // GUI aufrufen
        Cards calc = new Cards();
        calc.main();
    }
}