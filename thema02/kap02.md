# 2.2. Klassen, Interfaces, Ableitung, Vererbung

Allg: Darstellung in Klassendiagramm

## Klassen
* Baupläne nach denen Objekte (Instanzen von Klassen) modelliert werden
* Konstruktor, Methoden und Variablen formen zusammen eine Klasse
* Programmteile/Klassen wiederverwenden
* Klassen werden in verschiedenen Packages zusammengefasst

* Innere Klassen
    * Klassen und Interfaces können in Java grundsätzlich ineinander verschachtelt werden.
    * Eine "Inner Class" wird innerhalb des Codeblocks einer anderen
Klasse vereinbart.
    * Ziel: Definition von Hilfsklassen möglichst nahe an der Stelle, wo sie
gebraucht werden.


* Wrapper Klassen
    * Eine Wrapper-Klasse kapselt die jeweilige primitive Variable in einem sehr einfachen Objekt
    * Bsp:
           boolean -> java.lang.Boolean
           int -> java.lang.Integer
           char -> java.lang.Character


* Enumerations
    * Aufzählungsklasse
    * Bsp:

            ```java 
            //Die Enumeration wird in Java wie eine lokale Klasse behandelt
            enum Jahreszeiten {FRUEHLING, SOMMER, HERBST, WINTER}
            ```

## Modifier
* private
* protected
* public
* package (default)
* static
    * non-access Modifier
    * Auf statische Attribute/Methoden kann zugegriffen werden, ohne ein Objekt einer Klasse zu erstellen.
    * Alle Instanzen der Klasse teilen sich die statischen Attribute/Methoden
    * Methoden, die nicht vom Zustand des Objekts abhängen, sondern in irgendeiner Weise mit der Klasse selbst verbunden sind, können static gemacht werden
        * String.valueOf() hängt nicht von einem String ab -> ist eine static Methode
        * myString.length() hängt vom String ab -> nicht static
    * Zugriff mittels `Klassenname.Variable` oder `Klassenname.Methode()`  
* final
    * non-access Modifier
    * Macht eine Klasse, Methode oder Variable unveränderbar, nachdem diese einmal initialisieren wurden. -> Nicht vererbbar oder überschreibbar
* transient
    * non-access Modifier
    * Kennzeichnet Variablen, die bei der Serialisierung eines Objektes nicht mit gespeichert werden sollen
* volatile
    * non-access Modifier
    * Ermöglicht Threadsafety (laut Andi)
    * Teilt der JVM mit, dass ein Thread, der auf die Variable zugreift, immer seine eigene private Kopie der Variable mit der Hauptkopie im Speicher zusammenführen muss.
    * Beim Zugriff auf eine volatile Variable werden alle zwischengespeicherten Kopien der Variablen im Hauptspeicher synchronisiert.
    * Volatile kann nur auf Instanzvariablen angewendet werden, die vom Typ Objekt oder privat sind.

## Interfaces
* Interface = Schnittstelle
* 1 Klasse kann nur von 1er Klasse erben aber mehrere Interfaces implementieren
    * Ersatzkonstrukt für Mehrfachvererbung
* Stellt Konstanten und public abstract Methoden (Methodendeklarationen) zur Verfügung, die dann in der Klasse ausprogrammiert werden müssen
* Eine Klasse kann ein Interface mittels `public class ClassX implements InterfaceY` implementieren

## Vererbung/Ableitung
* In Java kann eine abgeleitete Klasse nur genau eine Vater-/Basisklasse haben.
* Das Erben von mehr als einer Superklasse (Mehrfachvererbung) ist nicht möglich.
* Die Klassen können beliebig tief geschachtelt werden, wobei ganz oben die Klasse Object steht
* Merkmale bereits vorhandener Klassen werden auf abgeleitete Klassen übertragen
    * Klassenvariablen & Methoden
    * Konstruktoren werden nicht vererbt, können jedoch mit super() aufgerufen werden
* Geerbte Variablen und Methoden können überlagert werden (@Overload) (siehe Kapitel 03)
* Eine Klasse kann mittels `public class ClassX extends ClassY` von einer Klasse ableiten

## Abstract Class/Method
* Abstrakte Klassen können nicht instanziert werden
* Enthalten abstrakte Methoden, welche erst später von abgeleiteten Klassen implementiert werden
* Abstrakte Methoden sind Methoden, die ohne Implementierung angegeben werden
* Erst wenn alle abstrakten Methoden der Superklasse implementiert worden sind, kann die Subklasse instanziert werden.
