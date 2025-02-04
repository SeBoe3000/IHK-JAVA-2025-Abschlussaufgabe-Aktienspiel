Die Confluence-Dokumentation ist unter https://docush.atlassian.net/l/cp/TP2DakoE vorhanden.

Beim Aktienspiel erhalten die Spieler am Ende jeder Runde eine Dividende, aus:
-	Generelle Dividende von 10% vom Aktienwert zum Zeitpunkt der Ausschüttung
-	Sonderdividende aus dem Kassenbestand der Unternehmen von 30% für die Person mit den meisten Aktien und 10% für die Person mit den zweitmeisten Aktien
Das Programm soll die Berechnung ermöglichen und solange speichern, bis das Programm oder Spiel beendet ist. Die Daten jeder Runde sind in einer Datei oder Datenbank zu speichern.
Optionale Punkte ist eine GUI, GitHub, Speicherung von Spielerdaten(Namen, Alter) und Abfragen zum Spielstand.

Weitere Informationen:
-	Die Dividende wird für alle zum gleichen Zeitpunkt berechnet (Ende der Runde).
-	Alle Personen erhalten ein Startkapital von 5.000. 
-	Für jedes Unternehmen sind 100 Aktien im Umlauf. Von anderen Spielern zu kaufen, wenn die Aktien eines Unternehmens ausverkauft sind, ist nicht teil der Aufgabe.
-	Eine Person kann maximal 30 Aktien kaufen.
-	Die Spieler kaufen nacheinander. Die Aktien können verkauft werden (alle Aktien werden in dieser Arbeit am Ende jeder Runde automatisch verkauft.)
-	Der niedrigste Aktienkurs ist 10€.
-	Der Kassenbestand soll zwischen 0 und 100.000 liegen.
-	Der Aktienwert und Kassenbestand sollen angegeben werden oder vom Zufallsgenerator kommen.
-	Zwischen 3 und 14 Personen und Aktien können am Spiel teilnehmen.
-	Nach dem Kauf der Aktien und Festlegung der Unternehmenswerte wird die Dividende und das Kapital berechnet. Das Kapital ist das Startkapital für die nächste Runde.
-	Bei zwei oder mehreren ersten Plätzen sollen die 40% auf die Anzahl der ersten Plätze verteilt werden. Gibt es nur einen ersten Platz und keinen zweiten, bekommt dieser die 40%
-	Bei zwei oder mehreren zweiten Plätzen sollen die 10% auf die Anzahl der zweiten Plätze verteilt werden.
-	Es werden keine Steuern gezahlt von den Privatpersonen. 20% vom Kassenbestand bezahlen die Unternehmen an Steuern (nicht Teil dieser Arbeit)
-	Der Startkurs von einem Unternehmen muss angegeben werden können.
-	Bruchstücke von Aktien können nicht erworben werden.

Anlage vom Container in Docker mit dem externen Port 1731, dem Namen Abschlussaufgabe und der Datenbank aktienrechner:
- Voraussetung: Docker Desktop geöffnet und Postgres heruntergeladen (docker pull postgres)
- docker run --name Abschlussaufgabe -e POSTGRES_PASSWORD=verysecure -p 1731:5432 -d postgres
- docker start Abschlussaufgabe
- docker exec -it Abschlussaufgabe psql -U postgres
- CREATE DATABASE aktienrechner;
- \c aktienrechner

Für jede weitere Verbindung durchführen:
- docker exec -it Abschlussaufgabe psql -U postgres
- \c aktienrechner
