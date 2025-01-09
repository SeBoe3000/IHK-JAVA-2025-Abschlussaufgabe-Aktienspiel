import Datenbank.Tabellen;
import Frontend.Start;

public class Aktienspiel {
    public static void main(String[] args) {
        // Tabellen erstellen, falls noch nicht vorhanden
        Tabellen.createTableall();
        // GUI aufrufen
        Start calc = new Start();
        calc.main();
    }
}
