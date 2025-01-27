package Frontend.Checks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EingabenCheck {

    /* Überprüfung String auf gültige Fließkommazahl, d.h.:
    - Nur Zahlen und Punkte sind zulässige Zeichen
    - Nur ein Punkt darf vorhanden sein.
    - bei NOTNULL: Mindestens eine Zahl zwischen 1 und 9 muss angegeben sein.
    - bei NULL: darf auch Null sein
    - bei Empty: darf auch leer sein
    - Darf nicht Infinity sein
     */
    public static boolean isValidFloat(String eingabe, String art){
        boolean isValid = true;
        Integer anzahlPunkte = 0;
        Integer anzahlZahl = 0;
        Integer anzahlZahlNull = 0;

        for(int i = 0; i < eingabe.length(); i++){
            if(!((eingabe.charAt(i) >= '0' && eingabe.charAt(i) <= '9')||eingabe.charAt(i) == '.')){
                isValid = false;
                break;
            }
            if((eingabe.charAt(i) >= '1' && eingabe.charAt(i) <= '9')){
                anzahlZahl ++;
            }
            if(eingabe.charAt(i) == '0'){
                anzahlZahlNull ++;
            }
            if(eingabe.charAt(i) == '.'){
                anzahlPunkte ++;
            }
        }
        // System.out.println("Punkte: " + anzahlPunkte + " Zahlen: " + anzahlZahl + " Nullen: " + anzahlZahlNull);

        // nur ein Punkt ist gültig
        if (anzahlPunkte > 1) {
            isValid = false;
        }

        // eine Zahl von 1-9 muss enthalten sein
        if (art == "NOTNULL" && anzahlZahl == 0){
            isValid = false;
        }

        // es muss mindestens eine 0 vorkommen
        if(art == "NULL" && anzahlZahlNull == 0 && anzahlZahl == 0) {
                isValid = false;
        }

        // Abfangen zu großer Zahlen
        // Infinity abfangen und dabei leere Eingabe berücksichtigen
        if (anzahlZahl > 0) {
            try {
                Float eingabeZahl = Float.parseFloat(eingabe);
                if (Float.isInfinite(eingabeZahl)) {
                    isValid = false;
                }
            } catch (Exception e) {
                isValid = false;
            }
        }
        return isValid;
    }

    /* Überprüfung String auf gültige Integer, d.h:
    - Nur Zahlen sind zulässige Zeichen
    - bei NOTNULL: Mindestens eine Zahl zwischen 1 und 9 muss angegeben sein.
    - bei NULL: darf auch 0 sein
    - bei EMPTY: darf auch leer sein
    - Abfangen von zu großer Zahl, durch try.
    */
    public static boolean isValidInteger(String eingabe, String art){
        boolean isValid = true;
        Integer anzahlZahl = 0;
        Integer anzahlZahlNull = 0;

        for(int i = 0; i < eingabe.length(); i++){
            if(!(eingabe.charAt(i) >= '0' && eingabe.charAt(i) <= '9')){
                isValid = false;
                break;
            }
            if((eingabe.charAt(i) >= '1' && eingabe.charAt(i) <= '9')){
                anzahlZahl ++;
            }
            if(eingabe.charAt(i) == '0'){
                anzahlZahlNull ++;
            }
        }

        // eine Zahl von 1-9 muss enthalten sein
        if(art == "NOTNULL" && anzahlZahl == 0) {
            isValid = false;
        }

        // es muss mindestens eine 0 vorkommen
        if(art == "NULL" && anzahlZahlNull == 0 && anzahlZahl == 0) {
            isValid = false;
        }

        // Abfangen zu großer Zahlen
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

    // Überprüfung Integer zwischen von bis (darf nicht leer, d.h. null sein)
    public static boolean isValidIntegerVonBis(String eingabe, Integer von, Integer bis){
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

    // Überprüfung Float zwischen von bis (darf nicht leer, d.h. null sein)
    public static boolean isValidFloatVonBis(String eingabe, Float von, Float bis) {
        boolean isValid = true;
        Float zahl = 0F;
        try {
            zahl = Float.parseFloat(eingabe);
        } catch (Exception e) {
            isValid = false;
        }
        if(zahl < von || zahl > bis){
            isValid = false;
        }
        return isValid;
    }
}

