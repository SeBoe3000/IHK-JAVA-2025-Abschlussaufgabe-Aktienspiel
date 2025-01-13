package Frontend.ActionListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EingabenCheck {

    /* Überprüfung String auf gültige Fließkommazahl, d.h.:
    - Nur Zahlen und Punkte sind zulässige Zeichen
    - Nur ein Punkt darf vorhanden sein.
    - Mindestens eine Zahl zwischen 1 und 9 muss angegeben sein. (Dadurch Überprüfung auf nicht leerer Wert)
    - Darf nicht Infinity sein
    - Darf nicht leer sein
     */
    public static boolean isValidFloat(String eingabe){
        boolean isValid = true;
        Integer anzahlPunkte = 0;
        Integer anzahlZahl = 0;

        for(int i = 0; i < eingabe.length(); i++){
            if(!((eingabe.charAt(i) >= '0' && eingabe.charAt(i) <= '9')||eingabe.charAt(i) == '.')){
                isValid = false;
                break;
            }
            if((eingabe.charAt(i) >= '1' && eingabe.charAt(i) <= '9')){
                anzahlZahl ++;
            }
            if(eingabe.charAt(i) == '.'){
                anzahlPunkte ++;
            }
        }
        //System.out.println("anzahlPunkte " + anzahlPunkte + " anzahlZahl " + anzahlZahl);
        if (anzahlPunkte > 1 || anzahlZahl == 0){
            isValid = false;
        }
        // Infinity abfangen und dabei leere Eingabe berücksichtigen
        try {
            Float eingabeZahl = Float.parseFloat(eingabe);
            if (Float.isInfinite(eingabeZahl)){
                isValid = false;
            }
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

    /* Überprüfung String auf gültige Integer, d.h:
    - Nur Zahlen zulässige Zeichen
    - Mindestens eine Zahl zwischen 1 und 9 muss angegeben sein. (Dadurch Überprüfung auf nicht leerer Wert)
    - wird per try versucht zu parsen, falls erfolglos deutet dies auf eine zu große Zahl hin.
    */
    public static boolean isValidInteger(String eingabe){
        boolean isValid = true;
        Integer anzahlZahl = 0;

        for(int i = 0; i < eingabe.length(); i++){
            if(!(eingabe.charAt(i) >= '0' && eingabe.charAt(i) <= '9')){
                isValid = false;
                break;
            }
            if((eingabe.charAt(i) >= '1' && eingabe.charAt(i) <= '9')){
                anzahlZahl ++;
            }
        }
        if (anzahlZahl == 0){
            isValid = false;
        }

        try {
            Integer eingabeZahl = Integer.parseInt(eingabe);
        } catch (Exception e) {
            isValid = false;
            // System.out.println("Zu große Zahl");
        }

        return isValid;
    }

    /* Überprüfung String auf gültige Integer, d.h:
    - Nur Zahlen oder leere Eingabe als zulässige Zeichen
    - Sofern mind. eine Zahl zwischen 1 und 9 angegeben ist, wird per try versucht zu parsen, falls erfolglos deutet dies auf eine zu große Zahl hin.
    */
    public static boolean isValidIntegerNull(String eingabe){
        boolean isValid = true;
        Integer anzahlZahl = 0;

        for(int i = 0; i < eingabe.length(); i++){
            if(!(eingabe.charAt(i) >= '0' && eingabe.charAt(i) <= '9')){
                isValid = false;
                break;
            }
            if((eingabe.charAt(i) >= '1' && eingabe.charAt(i) <= '9')){
                anzahlZahl ++;
            }
        }
        if (anzahlZahl > 0) {
            try {
                Integer eingabeZahl = Integer.parseInt(eingabe);
            } catch (Exception e) {
                isValid = false;
                // System.out.println("Zu große Zahl");
            }
        }
        return isValid;
    }

    // Überprüfung Integer zwischen von bis (darf nicht leer, d.h. null sein)
    public static boolean isValidIntegerVonBis(String eingabe,Integer von, Integer bis){
        boolean isValid = true;
        Integer zahl = 0;
        try {
            zahl = Integer.parseInt(eingabe);
        } catch (Exception e) {
            isValid = false;
        }
        if(zahl < von || zahl > bis){
            isValid = false;
        }
        return isValid;
    }

    /* Überprüfung String auf
    - mind. ein Zeichen.
    - kein DROP TABLE, DELETE, GRANT, REVOKE vorhanden
    - kein OR mit Leerzeichen davor und danach vorhanden */
    public static boolean isValidString(String eingabe){
        boolean isValid = true;
        // Mind. ein Zeichen muss vorhanden sein
        if(eingabe.equals("")){
            isValid = false;
        } else {
            isValid = true;
        }
        // Kein Drop, Delete, Grant, Revoke oder or mit Leerzeichen davor und danach vorhanden
        boolean injection = false;
        String os = System.getProperty("os.name");
        Pattern pattern;
        if (os.contains("Wind")) {
            pattern = Pattern.compile("([dD][rR][oO][pP]|[dD][eE][lL][eE][tT][eE]|[gG][rR][aA][nN][tT]|[rR][eE][vV][oO][kK][eE]|\\s[oO][rR]\\s)");
        } else {
            pattern = Pattern.compile("([dD][rR][oO][pP]|[dD][eE][lL][eE][tT][eE]|[gG][rR][aA][nN][tT]|[rR][eE][vV][oO][kK][eE]|/s[oO][rR]/s)");
        }
        Matcher matcher = pattern.matcher(eingabe);
        injection = matcher.find();
        // System.out.println("injection: " + injection);
        if(injection == true){
            isValid = false;
        }

        return isValid;
    }

    /* Überprüfung String auf Datumsformat
    - Prüfung auf richtiges Datums- / Uhrzeitformat u
    */
    public static boolean isValidDatum(String eingabe){
        boolean isValid = true;

        // Überprüfung auf korrektem Aufbau
        String os = System.getProperty("os.name");
        Pattern pattern;
        if (os.contains("Wind")) {
            pattern = Pattern.compile("(^[0-9]{4}(-[0-9]{2}){2}\\s([0-9]{2}:){2}[0-9]{2}$)");
        } else {
            pattern = Pattern.compile("(^[0-9]{4}(-[0-9]{2}){2}/s([0-9]{2}:){2}[0-9]{2}$)");
        }
        Matcher matcher = pattern.matcher(eingabe);
        boolean datum = matcher.find();

        if(datum == false) {
            isValid = false;
        }
        return isValid;
    }


    /* Überprüfung String auf gültiges Datum
    - Datums- / Uhrzeitformat und in Bestandteile für Prüfung zerlegen
    - Prüfung auf Tage nicht größer 31, bei Monat
    - Prüfung auf Monate nicht größer 12
    - Prüfung auf Tage nicht größer 30 bei Monat 04, 06, 09, 11
    - Prüfung auf Tage nicht größer 29 bei Monat 02
    - Prüfung auf Tage nicht größer 28 bei Monat 02 und keinem Schaltjahr
    - Prüfung auf Stunden nicht größer 23
    - Prüfung auf Minuten nicht größer 59
    - Prüfung auf Sekunden nicht größer 59
    */
    public static boolean isValidDatumCorrect(String eingabe){
        boolean isValid = true;
        // Eingabe in einzelne Bestandteile zerlegen
        Integer Jahr = Integer.parseInt(eingabe.substring(0,4));
        Integer Monat = Integer.parseInt(eingabe.substring(5,7));
        Integer Tag = Integer.parseInt(eingabe.substring(8,10));
        Integer Stunden = Integer.parseInt(eingabe.substring(11,13));
        Integer Minuten = Integer.parseInt(eingabe.substring(14,16));
        Integer Sekunden = Integer.parseInt(eingabe.substring(17,19));

        /*System.out.println("Jahr: " + eingabe.substring(0,4) + " Monat: " + eingabe.substring(5,7) + " Tag: " +
                eingabe.substring(8,10) + " Stunden: " + eingabe.substring(11,13) + " Minuten: " +
                eingabe.substring(14,16) + " Sekunden: " + eingabe.substring(17,19));
         */

        if (Tag > 31 ||
                Monat > 12 ||
                ((Monat == 4 || Monat == 6 || Monat == 9 || Monat == 11) && Tag > 30 ) ||
                (Monat == 2 && Tag > 29) ||
                (Monat == 2 && Tag > 28 && !((Jahr % 4 == 0 && Jahr % 100 != 0) || Jahr % 400 == 0)) ||
                (Stunden > 23) ||
                (Minuten > 59) ||
                (Sekunden > 59)
        ) {
            isValid = false;
        }
        return isValid;
    }

    // Überprüfung Datum und Uhrzeit nicht in Zukunft
    public static boolean isValidDatumNotInFuture(String eingabe){
        boolean isValid = true;
        Timestamp eingabeTageszeit = Timestamp.valueOf(eingabe);
        // aktuelles Datum mit Uhrzeit in java Datum umwandeln
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp sqldate = Timestamp.valueOf(localDateTime);
        // System.out.println("sqldate ist: " + sqldate + " eingabe ist: " + eingabeTageszeit);
        if (eingabeTageszeit.after(sqldate)) {
            isValid = false;
        }

        return isValid;
    }



    // Überprüfung auf Länge von bis
    public static boolean isValidStringLaenge(String eingabe, Integer von, Integer bis){
        boolean isValid = true;
        Integer anzahlZahl = 0;

        for(int i = 0; i < eingabe.length(); i++){
            anzahlZahl ++;
        }
        if (anzahlZahl < von || anzahlZahl > bis){
            isValid = false;
        }
        return isValid;
    }
}

