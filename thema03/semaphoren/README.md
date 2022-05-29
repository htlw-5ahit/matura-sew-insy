# 4104_Synchronisation

## Aufgabe 1
- Erzeuge 2 Threads, die jeweils einen unterschiedlichen Namen bekommen sollen (siehe API Doc zu Threads)
- Starte beide Threads
- Der Ablauf der Threads soll synchronisiert sein, d.h. die Threads laufen quasi abwechselnd geben ihren Namen am Bildschirm aus
- Löse die Aufgabe mittels "Warten und Benachrichtigen"

***Beispiel:*** 
Thread1 heißt **"tick"**, Thread2 heißt **"tack"**, Ausgabe:

```
tick
tack
tick
tack
...
```

## Aufgabe 2
- Versuche diesselbe Aufgabe mit Semaphoren zu lösen

Ist die Funktion die gleiche? Könnte es hier zu Problemen kommen?
