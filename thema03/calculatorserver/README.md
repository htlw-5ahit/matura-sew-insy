# 4301_CalculatorServer

## Aufgabe:
Entwickle einen Server, der einfache Berechnungen in Form von Grundrechnungsarten (Addition/Subtraktion/Multiplikation/Division zweier Zahlen) durchführen kann.

### Server (Konsole) (35)
- der Server wartet auf eine eingehende Verbindung (Port 2000)
- sobald die Verbindung steht, wartet der Server auf Rechnungen
- wenn der Server eine Rechnung empfängt, berechnet er das Ergebnis und schickt es zurück an den Client
- im Anschluss ist der Server wieder im Empfangsmodus für neue Rechnungen
- überlege dir eine Möglichkeit, wie du verschiedene Rechenoperationen an den Server schicken kannst

### Client (Konsole) (35)
- schreib ein Konsolenprogramm, mit dem du Rechnungen an den CalculatorServer schicken kannst
- das Ergebnis wird in der Konsole ausgegeben
- du kannst mit dem Programm beliebig viele Rechnungen berechnen

### Client (JavaFX) (30)
- baue einen JavaFX fähigen Client mit einfacher GUI


## Tip:
- Erstelle ein Projekt mit mehreren Packages z.B. ```server``` und ```client```
- Du kannst in jedem Package eine eigene ```main``` Methode definieren und jeweils Server und Client über eine eigene Run-Configuration starten
- Beim Start erhälst du in der Konsole 2 Tabs - somit kannst du auch Konsolenoutput ansehen
