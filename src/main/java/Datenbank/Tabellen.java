package Datenbank;

public class Tabellen {
    /* Hinweis: In den Check-Bedingungen der Datenbank können keine dynamischen Werte geprüft werden.
     Daher werden diese in der Programmlogik geprüft, aber nicht zusätzlich auf Ebene der Datenbank. */

    // Stammdaten Personen
    static String createTablePersonen = "CREATE TABLE IF NOT EXISTS Personen" +
            "(" +
            "ID serial NOT NULL," +
            "Vorname Varchar(20) NOT NULL," +
            "Nachname Varchar(20) NOT NULL," +
            "Alter integer NOT NULL," +
            // ggf. Adresse hinzufügen
            "PRIMARY KEY(ID)," +
            "UNIQUE(Vorname, Nachname, Alter)," +
            "CONSTRAINT check_Alter CHECK (Alter > 17 AND Alter < 131)" +
            ")";

    // Stammdaten Aktien
    static String createTableAktien = "CREATE TABLE IF NOT EXISTS Aktien" +
            "(" +
            "ISIN char(12) NOT NULL," +
            "Name Varchar(50) NOT NULL," +
            "PRIMARY KEY(ISIN)" +
            ")";

    // Bewegungsdaten - Anzahl Aktien, Aktienkurs und Kassenbestand der Aktien pro Runde
    static String createTableAktienverlauf = "CREATE TABLE IF NOT EXISTS Aktienverlauf" +
            "(" +
            "ID serial NOT NULL," +
            "Runde integer NULL," +
            "AktieISIN char(12) NOT NULL," +
            "Aktienanzahl integer NULL," +
            "Aktienkurs real NOT NULL," +
            "Kassenbestand real NULL," +
            "PRIMARY KEY(ID)," +
            "UNIQUE(Runde, AktieISIN)," +
            "CONSTRAINT fk_Aktie FOREIGN KEY (AktieISIN) REFERENCES Aktien (ISIN)" +
            ")";

    // Bewegungsdaten - Kapitel der Personen pro Runde
    static String createTableKapitalverlauf = "CREATE TABLE IF NOT EXISTS Kapitalverlauf" +
            "(" +
            "ID serial NOT NULL," +
            "Runde integer NULL," +
            "PersonID integer NOT NULL," +
            // wird nach jeder Runde über die Transaktionen ermittelt.
            "Kapital real NOT NULL," +
            "PRIMARY KEY(ID)," +
            "UNIQUE(PersonID, Runde, Kapital)," +
            "CONSTRAINT fk_Person FOREIGN KEY (PersonID) REFERENCES Personen (ID)" +
            ")";

    // Bewegungsdaten - Transaktionen (Käufe und Dividenden) pro Runde
    static String createTableTransaktionen = "CREATE TABLE IF NOT EXISTS Transaktionen" +
            "(" +
            "Runde integer NOT NULL," +
            "PersonID integer NOT NULL," +
            "AktieISIN char(12) NOT NULL," +
            "Aktienanzahl integer NOT NULL," +
            // Aktienwert kann über Aktienverlauf ermittelt werden
            // Dividende wird nach Abschluss der Runde ermittelt
            "Dividende real NULL," +
            "PRIMARY KEY(Runde, PersonID, AktieISIN)," +
            "CONSTRAINT fk_Person FOREIGN KEY (PersonID) REFERENCES Personen (ID)," +
            "CONSTRAINT fk_Aktie FOREIGN KEY (AktieISIN) REFERENCES Aktien (ISIN)" +
            ")";

    // Einstellungen
    static String createTableEinstellungen = "CREATE TABLE IF NOT EXISTS Einstellungen" +
            "(" +
            "Typ char(3) NOT NULL," +
            "Einstellung text," +
            "PRIMARY KEY(Typ)" +
            ")";

    // Einstellungen befüllen mit Defaults
    static String insertDefaultEinstellungen = "INSERT INTO Einstellungen VALUES" +
            "('AKT', '100')," +
            "('PER', '5000,N')," +
            "('ORD', '0.0,100000.0,10.0')," +
            "('TRN', '30,3,14,3,14,30.0,10.0')," +
            "('RND', '1')" +
            " ON CONFLICT DO NOTHING";

    // Stammdaten Personen
    public static void createTablePersonen(){
        SQL.table(createTablePersonen);
    }

    // Stammdaten Aktien
    public static void createTableAktien(){
        SQL.table(createTableAktien);
    }

    // Bewegungsdaten - Anzahl Aktien, Aktienkurs und Kassenbestand der Aktien pro Runde
    public static void createTableAktienverlauf(){
        SQL.table(createTableAktienverlauf);
    }

    // Bewegungsdaten - Kapitel der Personen pro Runde
    public static void createTableKapitalverlauf(){
        SQL.table(createTableKapitalverlauf);
    }

    // Bewegungsdaten - Transaktionen (Käufe und Dividenden) pro Runde
    public static void createTableTransaktionen(){
        SQL.table(createTableTransaktionen);
    }

    // Einstellungen
    public static void createTableEinstellungen(){
        SQL.table(createTableEinstellungen);
    }

    // Einstellungen befüllen mit Defaults
    public static void insertDefaultEinstellungen(){
        SQL.table(insertDefaultEinstellungen);
    }

    public static void createTableall(){
        // Gemeinsam aufrufen
        SQL.table(createTablePersonen + ";" + createTableAktien + ";" + createTableAktienverlauf + ";" +
                createTableKapitalverlauf + ";" + createTableTransaktionen + ";" +
                createTableEinstellungen + ";" + insertDefaultEinstellungen);

        // Nacheinander aufrufen
        /*createTablePersonen();
        createTableAktien();
        createTableAktienverlauf();
        createTableKapitalverlauf();
        createTableTransaktionen();
        createTableEinstellungen();
        insertDefaultEinstellungen();
         */
    }

    private static void dropTableall() {
        SQL.table("DROP TABLE Transaktionen;" +
                "DROP TABLE Kapitalverlauf;" +
                "DROP TABLE Aktienverlauf;" +
                "DROP TABLE Aktien;" +
                "DROP TABLE Personen;" +
                "DROP TABLE Einstellungen;");
    }

    public static void main(String[] args) {
        // dropTableall();
        createTableall();
    }
}
