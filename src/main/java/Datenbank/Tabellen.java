package Datenbank;

public class Tabellen {

    // Stammdaten Personen
    public static void createTablePersonen(){
        SQL.table("CREATE TABLE IF NOT EXISTS Personen" +
                "(" +
                "ID serial NOT NULL," +
                "Vorname Varchar(20) NOT NULL," +
                "Nachname Varchar(20) NOT NULL," +
                "Alter integer NOT NULL," +
                // ggf. Adresse hinzufügen
                "PRIMARY KEY(ID)," +
                "UNIQUE(Vorname, Nachname, Alter)," +
                "CONSTRAINT check_Alter CHECK (Alter > 17 AND Alter < 131)" +
                ")");
    }

    // Stammdaten Aktien
    public static void createTableAktien(){
        SQL.table("CREATE TABLE IF NOT EXISTS Aktien" +
                "(" +
                "ISIN char(12) NOT NULL," +
                "Name Varchar(30) NOT NULL," +
                "PRIMARY KEY(ISIN)" +
                ")");
    }

    // Bewegungsdaten - Anzahl Aktien, Aktienkurs und Kassenbestand der Aktien pro Runde
    public static void createTableAktienverlauf(){
        SQL.table("CREATE TABLE IF NOT EXISTS Aktienverlauf" +
                "(" +
                "ID serial NOT NULL," +
                "Runde integer NOT NULL," +
                "AktieISIN char(12) NOT NULL," +
                "Aktienanzahl integer  NOT NULL," +
                "Aktienkurs real NOT NULL," +
                "Kassenbestand real NOT NULL," +
                "PRIMARY KEY(ID)," +
                "UNIQUE(Runde, AktieISIN)," +
                "CONSTRAINT fk_Aktie FOREIGN KEY (AktieISIN) REFERENCES Aktien (ISIN)," +
                "CONSTRAINT check_Aktienanzahl CHECK (Aktienanzahl = 100)," +
                "CONSTRAINT check_Aktienkurs CHECK (Aktienkurs >= 10)," +
                "CONSTRAINT check_Kassenbestand CHECK (Kassenbestand >= 0 AND Kassenbestand <= 100000)" +
                ")");
    }

    // Bewegungsdaten - Kapitel der Personen pro Runde
    public static void createTableKapitalverlauf(){
        SQL.table("CREATE TABLE IF NOT EXISTS Kapitalverlauf" +
                "(" +
                "ID serial NOT NULL," +
                "Runde integer NOT NULL," +
                "PersonID integer NOT NULL," +
                // wird nach jeder Runde über die Transaktionen ermittelt.
                "Kapital integer NOT NULL," +
                "PRIMARY KEY(ID)," +
                "UNIQUE(PersonID, Runde, Kapital)," +
                "CONSTRAINT fk_Person FOREIGN KEY (PersonID) REFERENCES Personen (ID)" +
                ")");
    }

    // Bewegungsdaten - Transaktionen (Käufe und Dividenden) pro Runde
    public static void createTableTransaktionen(){
        SQL.table("CREATE TABLE IF NOT EXISTS Transaktionen" +
                "(" +
                "Runde integer NOT NULL," +
                "PersonID integer NOT NULL," +
                "AktieISIN char(12) NOT NULL," +
                "Aktienanzahl integer NOT NULL," +
                // Aktienwert kann über Aktienverlauf ermittelt werden
                // Dividende wird nach Abschluss der Runde ermittelt
                "Dividende integer NULL," +
                "PRIMARY KEY(Runde, PersonID, AktieISIN)," +
                "CONSTRAINT fk_Person FOREIGN KEY (PersonID) REFERENCES Personen (ID)," +
                "CONSTRAINT fk_Aktie FOREIGN KEY (AktieISIN) REFERENCES Aktien (ISIN)" +
                ")");
    }

    public static void createTableall(){
        createTablePersonen();
        createTableAktien();
        createTableAktienverlauf();
        createTableKapitalverlauf();
        createTableTransaktionen();
    }

    private static void dropTableall() {
        SQL.table("DROP TABLE Transaktionen;" +
                "DROP TABLE Kapitalverlauf;" +
                "DROP TABLE Aktienverlauf;" +
                "DROP TABLE Aktien;" +
                "DROP TABLE Personen");
    }

    public static void main(String[] args) {
        // dropTableall();
        createTableall();
    }
}
