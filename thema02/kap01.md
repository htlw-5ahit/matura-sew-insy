# 2.1. Datenkapselung und Sichtbarkeit, Zugriffsmethoden

## Datenkapselung und Sichtbarkeit
* Datenkapselung ist das Verbergen von Attributen und Methoden durch Einschränkung des Zugriffs von Ausserhalb der Klasse.
* public (+) - Zugriff von außerhalb und innerhalb der Klasse möglich
* protected (#) - Zugriff für alle Elemente innerhalb des eigenen Pakets und von Ableitungen (auch in anderen Packages) der Klasse
* package (~) - Zugriff für alle Elemente innerhalb des eigenen Pakets (Default in Java)
* private (-) - Zugriff nur innerhalb der Klasse möglich

## Zugriffsmethoden
* Public: Zugriff direkt auf Variablen mit objekt.variable
    * Bsp: ```Auto a = new Auto();
            sout(a.motor);```
* Normalerweise: Zugriff über Getter & Setter
