import Datenbank.Tabellen;
import Frontend.Cards;
import Frontend.Programme.Start;

public class Aktienspiel {
    public static void main(String[] args) {
        // Tabellen erstellen, falls noch nicht vorhanden
        Tabellen.createTableall();
        // GUI aufrufen
        //Start calc = new Start();
        Cards calc = new Cards();
        calc.main();
    }
}