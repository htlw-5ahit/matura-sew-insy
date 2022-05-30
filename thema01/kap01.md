# 1.1. C-Grundlagen

## Grundsätzlich
* Kompakte, strukturierte, typisierte, hardwarenahe, portable, höhere Programmiersprache
* Ein-/Ausgabe ist nicht im Befehlsumfang enthalten -> Funktionen der
Standardbibliothek
* Ein Zeiger repräsentiert eine Adresse und nicht wie eine Variable einen Wert. Will man auf den Wert der Adresse zugreifen, auf die ein Zeiger zeigt, muss * vor den Namen gesetzt werden.

```c
int zahl = 7;
int *zeiger;
zeiger = &zahl;
printf("Zeiger-Wert: %d\n", *zeiger);
```

## Quellcode-Übersetzen-Syntax-Semantik
* C-Quellcode muss Grammatik (Syntax) & fachliche Anforderung (Semantik) erfüllen
* Semikolon am Ende & Kommentare wie in Java
* Geschriebener Quellcode wird vom Compiler in Objektcode übersetzt und
danach vom Linker mit Bibliotheken zu einem ausführbaren Programm gebunden
* Der Compiler ruft vor der eigentlichen Übersetzung den Präprozessor auf, der die
Präprozessordirektive (Ganz oben #define, #include, usw.) auswertet

## H&C-File
* C-Quellcode setzt sich  aus Quellcode-Dateien (`.c`) zusammen, die Header-Dateien mittels `#include` einbinden (`.h`);
*  `.c`-File: SourceCode
*  `.h`-File: Funktionsdeklarationen/Prototypen, Konstanten, usw., die in mehreren `.c`-Files verwendet werden

## Primitive Datentypen (Datentyplänge, Unsigned/Signed)

| Type                   | Size (bytes)                  | Format Specifier |
|------------------------|-------------------------------|------------------|
| int                    | at least 2, usually 4         | %d, %i           |
| char                   | 1                             | %c               |
| float                  | 4                             | %f               |
| double                 | 8                             | %lf              |
| short int              | 2 usually                     | %hd              |
| unsigned int           | at least 2, usually 4         | %u               |
| long int               | at least 4, usually 8         | %ld, %li         |
| long long int          | at least 8                    | %lld, %lli       |
| unsigned long int      | at least 4                    | %lu              |
| unsigned long long int | at least 8                    | %llu             |
| signed char            | 1                             | %c               |
| unsigned char          | 1                             | %c               |
| long double            | at least 10, usually 12 or 16 | %Lf              |

* Mit `sizeof(variable)` erhält man die Größe eines Datentyps in Byte
* char ≤ short int ≤ int ≤ long int ≤ long long int
* float, double & long double sind immer signed
* signed: positiv & negativ -> Wertebereich 50% pos, 50% neg
    * Bsp: signed char von -127 bis 127
* unsigned: nur positiv -> Wertebereich 100% pos
    * Bsp: unsigned char von 0 bis 256

## Variablen-, Konstanten & Funktionen
* Buchstaben (ausgenommen Umlaute, ß), Ziffern und Unterstrich
* Mit Buchstaben beginnen
* Konstanten mit `#define NAME Wert`

## Ein-/Ausgabe
* Erfordert `#include <stdio.h>`
* Formatierung mit `sprintf(array, "Format", variablen...)`
* Ausgabe mit `printf("Text");`
* Einlesen mit `scanf("%d",&number);`
* Zeichenkette einlesen mit `fgets(array, laenge, stdin/File)`

## Prototypen
* Deklaration einer Funktion – inklusive Angaben über Anzahl, Typ der Parameter und Typ des Rückgabewertes – getrennt von ihrer Implementierung

```c
// enthält unter anderem den Funktionsprototypen für printf():
#include <stdio.h>

// Prototypdeklaration, die Parameterbezeichner sind optional:
double summe(double zahl1, double zahl2);

int main(void) {
    // Aufruf der Funktion; ohne Funktionsprototyp wären hier
    // Argumenttyp (int) und Parametertyp (double) inkompatibel:
    printf("2 + 3 = %g\n", summe(2, 3));
    return 0;
}

// Definition der Funktion:
double summe(double zahl1, double zahl2) {
    return zahl1 + zahl2;
}
```