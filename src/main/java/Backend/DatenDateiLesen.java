package Backend;

import Datenbank.SQL;
import Frontend.ActionListenerUpdate.EinstellungenAktienListener;
import Frontend.ActionListenerUpdate.EinstellungenAktienverlaufListener;
import Frontend.ActionListenerUpdate.EinstellungenPersonenListener;
import Frontend.Checks.EingabenCheck;
import Frontend.Programme.Stammdaten.BeispieldatenImportieren;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DatenDateiLesen {

    public static Object ermittelnFile(String file){
        File myFile;
        String os = System.getProperty("os.name");
        if (os.contains("Wind")) {
            myFile = new File("src\\main\\java\\Dateien\\" + file + ".csv");
        } else {
            myFile = new File("src/main/java/Dateien/" + file + ".csv");
        }
        return myFile;
    }

    // Import Personen
    public static ArrayList<ElementPerson> readPerson(){
        ArrayList<ElementPerson> elementsList = new ArrayList<>();
        File myFile = new File(String.valueOf(ermittelnFile("Personen")));
        try{
            Scanner myFileReader = new Scanner(myFile);
            String line;
            line = myFileReader.nextLine(); // erste Zeile nicht berücksichtigen

            while(myFileReader.hasNextLine()){
                Boolean insert = true;
                line = myFileReader.nextLine();
                String[] splitted = line.split(";");

                // Prüfungen zu Vor- und Nachname
                try {
                    if(!EingabenCheck.isValidString(splitted[0])|| !EingabenCheck.isValidString(splitted[1])){
                        BeispieldatenImportieren.errorMessages.add("Vorname und/oder Nachname ungültig. Daten: " + line);
                        insert = false;
                    }
                    if(!EingabenCheck.isValidStringLaenge(splitted[0],0,20) || !EingabenCheck.isValidStringLaenge(splitted[1],0,20)){
                        BeispieldatenImportieren.errorMessages.add("Vorname und/oder Nachname ist zu lange. Darf maximal 20 Stellen haben. Daten: " + line);
                        insert = false;
                    }
                } catch (Exception e){
                    BeispieldatenImportieren.errorMessages.add("Kein Vorname und/oder Nachname angegeben. Daten: " + line);
                    insert = false;
                    // e.printStackTrace();
                }

                // Prüfungen zum Alter
                Integer alter = 0;
                try{
                    alter = Integer.parseInt(splitted[2]);
                    if(alter < 18 || alter > 130){
                        BeispieldatenImportieren.errorMessages.add("Das Alter " + alter + " von " + splitted[0] + " " +
                                splitted[1] + " ist ungültig. Es muss zwischen 18 und 130 sein.");
                        insert = false;
                    }
                }
                catch(Exception e){
                    BeispieldatenImportieren.errorMessages.add("Fehler beim Parsen vom Alter der importierten Person. Daten: " + line);
                    // e.printStackTrace();
                    insert = false;
                }

                // Prüfung Datensatz nicht vorhanden
                if(insert) {
                    if (SQL.checkElementAlreadyInDatenbankStringStringInteger(splitted[0], splitted[1], alter, "Vorname", "Nachname", "Alter", "Personen")) {
                        BeispieldatenImportieren.errorMessages.add("Der Datensatz befindet sich bereits in der Datenbank. Daten: " + line);
                        insert = false;
                    }
                }

                // Objekte erzeugen
                if(insert) {
                    ElementPerson element = new ElementPerson(splitted[0], splitted[1], alter);
                    elementsList.add(element);
                }
            }
        } catch (FileNotFoundException e){
            BeispieldatenImportieren.errorMessages.add("Die Datei Personen existiert nicht.");
            // e.printStackTrace();
        }
        return elementsList;
    }

    // Import Aktien
    public static ArrayList<ElementAktie> readAktie(){
        ArrayList<ElementAktie> elementsList = new ArrayList<>();
        File myFile = new File(String.valueOf(ermittelnFile("Aktien")));
        try{
            Scanner myFileReader = new Scanner(myFile);
            String line;
            line = myFileReader.nextLine(); // erste Zeile nicht berücksichtigen

            while(myFileReader.hasNextLine()){
                Boolean insert = true;
                line = myFileReader.nextLine();
                String[] splitted = line.split(";");

                // Prüfungen ISIN und Name
                try {
                    if(!EingabenCheck.isValidString(splitted[0]) || !EingabenCheck.isValidStringLaenge(splitted[0],12,12)){
                        BeispieldatenImportieren.errorMessages.add("ISIN ist ungültig. Sie muss 12 Stellen haben. Daten: " + line);
                        insert = false;
                    }
                    if(!EingabenCheck.isValidString(splitted[1]) || !EingabenCheck.isValidStringLaenge(splitted[1],0,50)){
                        BeispieldatenImportieren.errorMessages.add("Name ist ungültig. Er darf maximal 50 Stellen haben. Daten: " + line);
                        insert = false;
                    }
                } catch (Exception e){
                    BeispieldatenImportieren.errorMessages.add("Keine ISIN und/oder Name angegeben. Daten: " + line);
                    insert = false;
                    // e.printStackTrace();
                }

                // Prüfung Datensatz nicht vorhanden
                if(insert) {
                    if (SQL.checkElementAlreadyInDatenbankOneString(splitted[0], "isin","Aktien")) {
                        BeispieldatenImportieren.errorMessages.add("Der Datensatz befindet sich bereits in der Datenbank. Daten: " + line);
                        insert = false;
                    }
                }

                // Objekte erzeugen
                if(insert) {
                    ElementAktie element = new ElementAktie(splitted[0], splitted[1]);
                    elementsList.add(element);
                }
            }
        } catch (FileNotFoundException e){
            BeispieldatenImportieren.errorMessages.add("Die Datei Aktien existiert nicht.");
        }
        return elementsList;
    }

    // Import Startkapital
    public static ArrayList<ElementKapitalverlauf> readStartkapital(){
        ArrayList<ElementKapitalverlauf> elementsList = new ArrayList<>();
        File myFile = new File(String.valueOf(ermittelnFile("Startkapital")));
        try{
            Scanner myFileReader = new Scanner(myFile);
            String line;
            line = myFileReader.nextLine(); // erste Zeile nicht berücksichtigen

            while(myFileReader.hasNextLine()){
                Boolean insert = true;
                line = myFileReader.nextLine();
                String[] splitted = line.split(";");

                Integer personID = 0;
                Float kapital = 0F;

                // Prüfung PersonID
                try{
                    personID = Integer.parseInt(splitted[0]);

                    if(!SQL.checkElementAlreadyInDatenbankOneInteger(personID, "id","Personen")){
                        BeispieldatenImportieren.errorMessages.add("Die Person ist nicht in der Tabelle Personen vorhanden. Daten: " + line);
                    }
                } catch(Exception e){
                    BeispieldatenImportieren.errorMessages.add("Fehler beim Parsen der PersonID des importierten Startkapitals. Daten: " + line);
                    // e.printStackTrace();
                }

                // Prüfung Datensatz nicht vorhanden
                if(insert) {
                    if (SQL.checkElementAlreadyInDatenbankIntegerInteger(personID, 0, "id", "Runde", "Kapitalverlauf")) {
                        BeispieldatenImportieren.errorMessages.add("Der Datensatz befindet sich bereits in der Datenbank. Daten: " + line);
                        insert = false;
                    }
                }

                // Prüfung Startkapital
                // Ist das Startkapital nicht bearbeitbar, wird der Default aus den Einstellungen genommen.
                if(insert) {
                    if (EinstellungenPersonenListener.getEinstellungString("defaultStrartkapitalBearbeitbar").equals("N")) {
                        kapital = EinstellungenPersonenListener.getEinstellungFloat("defaultStrartkapital");
                        BeispieldatenImportieren.errorMessages.add("Kapital wurde mit " + kapital +
                                " überschrieben, da Default Startkapital nicht bearbeitbar ist. Daten: " + line);
                    } else {
                        try {
                            kapital = Float.parseFloat(splitted[1]);
                        } catch (Exception e) {
                            BeispieldatenImportieren.errorMessages.add("Fehler beim Parsen vom Betrag des importierten Startkapitals. Daten: " + line);
                            // e.printStackTrace();
                        }
                    }
                }

                // Objekte erzeugen
                if(insert) {
                    ElementKapitalverlauf element = new ElementKapitalverlauf(0, personID, kapital);
                    elementsList.add(element);
                }
            }
        } catch (FileNotFoundException e){
            BeispieldatenImportieren.errorMessages.add("Die Datei Startkapital existiert nicht.");
            // e.printStackTrace();
        }
        return elementsList;
    }

    // Import Startkurs
    public static ArrayList<ElementAktienverlauf> readStartkurs(){
        ArrayList<ElementAktienverlauf> elementsList = new ArrayList<>();
        File myFile = new File(String.valueOf(ermittelnFile("Startkurs")));
        try{
            Scanner myFileReader = new Scanner(myFile);
            String line;
            line = myFileReader.nextLine(); // erste Zeile nicht berücksichtigen

            while(myFileReader.hasNextLine()){
                Boolean insert = true;
                line = myFileReader.nextLine();
                String[] splitted = line.split(";");

                // Prüfung AktieIsin
                try {
                    // Aktie muss in Tabelle Aktien vorhanden sein.
                    if(!SQL.checkElementAlreadyInDatenbankOneString(splitted[0], "isin","Aktien")){
                        BeispieldatenImportieren.errorMessages.add("Die Aktie ist nicht in der Tabelle Aktien vorhanden. Daten: " + line);
                    }
                } catch (Exception e){
                    BeispieldatenImportieren.errorMessages.add("Keine AktienISIN angegeben. Daten: " + line);
                    insert = false;
                    // e.printStackTrace();
                }

                // Anzahl Aktien wird aus den Einstellungen ermittelt.
                Integer anzahl = EinstellungenAktienListener.getEinstellungInteger("maxAnzahlAktien");

                // Prüfung Aktienkurs
                Float kurs = 0F;
                try{
                    kurs = Float.parseFloat(splitted[1]);
                    if(!EingabenCheck.isValidFloatVonBis(splitted[1],EinstellungenAktienverlaufListener.getEinstellungFloat("minAktienkurs"), Float.MAX_VALUE)){
                        BeispieldatenImportieren.errorMessages.add("Der Kurs muss mindestens " +
                                EinstellungenAktienverlaufListener.getEinstellungFloat("minAktienkurs") + " sein. Daten: " + line);
                        insert = false;
                    }
                } catch(Exception e){
                    BeispieldatenImportieren.errorMessages.add("Fehler beim Parsen vom Kurs des importierten Aktienverlauf.");
                    // e.printStackTrace();
                }

                // Prüfung Datensatz nicht vorhanden
                if(insert){
                    if(SQL.checkElementAlreadyInDatenbankIntegerIntegerString(0,0, splitted[0], "Runde", "Runde", "AktieIsin", "Aktienverlauf")){
                        BeispieldatenImportieren.errorMessages.add("Der Datensatz befindet sich bereits in der Datenbank. Daten: " + line);
                    }
                }

                // Objekte erzeugen
                if(insert) {
                    ElementAktienverlauf element = new ElementAktienverlauf(0, splitted[0], anzahl, kurs, 0F);
                    elementsList.add(element);
                }
            }
        } catch (FileNotFoundException e){
            BeispieldatenImportieren.errorMessages.add("Die Datei Startkapital existiert nicht.");
            // e.printStackTrace();
        }
        return elementsList;
    }
}
