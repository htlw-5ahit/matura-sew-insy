# 614_Doppelt_verkettete_Liste

## Aufgabe
Schreibe ein C Programm, welches Artikel (artikel_name, artikel_nr, anzahl) in einer doppelt verketteten Liste verwaltet.

Folgende Punkte sind zu erfüllen:
- Die Liste ist nach artikel_name aufsteigend sortiert
- Verwende eigene Elemente ohne Datenspeicher für das erste und letzte Element (wie in der Stunde besprochen)
- Teste dein Programm, wichtig sind hier insbesondere die Ausnahmefälle (löschen von einem nicht existenten Element, löschen des ersten Elements, ...)
- Teile dein Programm in mehrere Komponenten: Testprogramm, Funktionalität (Verkette Liste) + dazugehöriges Headerfile"

Implementiere folgende Funktionalität:
- Hinzufügen eines Elements
 	- [x] das Element wird an der korrekten Stelle alphabetisch aufsteigend sortiert nach artikel_name in die Liste eingefügt
	- [x] existiert der Artikel bereits, so wird die Anzahl bei dem entsprechenden Artikel erhöht (Bsp. wir haben bereits 2 Bleistifte in unserer Liste gespeichert, 4 weitere kommen hinzu -> erhöhe die anzahl bei dem Artikel um 4 auf insg. 6)
- [x] Ausgabe alphabetisch aufsteigend bzw. absteigend - übergib dies als entsprechenden Parameter an die Funktion
- [x] Löschen eines Artikels anhand des artikel_name
- [x] Löschen eines Artikels anhand der artikel_nr
- [x] Entnahme einer bestimmten Anzahl eines Artikels (z.B. entnimm 2 Bleistifte)
- [x] Aufräumen (löschen der gesamten Liste)