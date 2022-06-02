# 7.5. UML Modell und Umsetzung

## Welches Diagramm eignet sich zur Datendarstellung im Datenbankbereich?

* ER-Modell

## In welche Gruppen werden die Diagramme von UML unterteilt?

* Strukturdiagramme
* Verhaltensdiagramme

## Nenne Beispiele und Einsatzgebiete für jede Gruppe.

* Strukturdiagramme
    * Klassendiagramm
* Verhaltensdiagramme
    * Sequenzdiagramm
    * UseCase Diagramm

## Was versteht man unter dem Begriff "ORM"?

* Object-Relation-Mapping
    * Java Persistance API
    * Objektrelationales Mapping

## Was versteht man unter dem Begriff „impedance mismatch“ in Hinblick auf Programmiersprachen und Datenbanken?

* Problematik verbindung objektrelationales Modell und relationale Modell

## Wie können Objekte am besten persistent gespeichert werden – geben Sie dazu ein Beispiel.

* Objektorientierte Datenbank
* Relationale Datenbank: Mapping zwischen Objekt und Datenbanktabelle => DAO-Klassen

## Erläutere die einzelnen Konzepte des Diagramms (Klassendiagramm).

* Multiplizität (Beziehungen)

## Wie gehe ich bei der Erstellung eines UML Modells vor.

* Hauptwörter werden Klassen
* Beziehungen
* Attribute zu Klassen (Attribut oder eigene Klasse)

## Wie werden diese in ein Relationenmodell umgesetzt?

* UML Klasssendiagramm -> Relationenmodell
* Wie bei ER-Modell

## Welche Elemente/Teile des Diagramms werden nicht umgesetzt bzw. gebraucht (JPA).

* Ausnahme von Attribute: `volatil`/`transient`

## Vergleichen Sie ER- und UML-Diagramm. Welche Vorteile bietet das UML-Modell gegenüber dem ER-Modell?

* Viel mehr Möglichkeiten
* Detailierte Beziehungen (Beispiel: Auto mit Rändern)
* Existenzabhängigkeit: Aggregation, Vererbung
