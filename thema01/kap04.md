# 1.4. Dateien und Fehlerbehandlung

## Dateien
* Datei öffnen
  - `FILE * fopen(const char *filename, const char *mode);`
  - Modi (können auch kombiniert werden)
    - r: read, lesen
    - w: write, schreiben
    - a: append, anhängen
    - b: binary, Binärmodus
    - t: text, Textmodus
* Datei schließen
  - `int fclose (FILE *fp);`
  - `int fcloseall();`
* Datei schreiben
  - Zeichenweise: `int fputc(int c, FILE *fp);`
  - Formatiert: `int fprintf(FILE *fp, const char *format [, arguments]);`
* Datei lesen
  - Zeichenweise: `int fgetc(FILE *fp);`
  -  Formatiert: `int fscanf(FILE *fp, char *format [, arguments]);`
* [Files Übung](https://github.com/htlw-5ahit/matura-sew-insy/blob/main/thema01/files/main.c)

## Fehlerbehandlung
* Fehler werden über Returnvalues weitergegeben -> Returnvalues überprüfen
* Die Benutzung der Bibliothek „errno.h” ist eine weit verbreitete Methode zur Fehlerbehandlung.
  - Wenn bei einem Systemaufruf ein Fehler auftritt, wird in der globalen Variable errno ein entsprechender Fehlerwert gesetzt. Mit den beiden Funktionen strerror() und perror() können diese Systemfehlermeldung ausgegeben werden.
* `void perror(const char *meldung);`
  - Wenn für meldung kein NULL-Zeiger angegeben wurde, wird der String meldung mit anschließendem Doppelpunkt, gefolgt von einer zu errno gehörenden Fehlermeldung, ausgegeben (mit abschließendem '\n'). Wird hingegen diese Funktion mit dem NULL-Zeiger aufgerufen, wird nur eine zu errno gehörende Fehlermeldung ausgegeben. Geschrieben wird diese Fehlermeldung auf die Standardfehlerausgabe (stderr).
  - Bsp:
    - Bei einem Fehler wird "Kann nicht aus Datei lesen : No such file or directory" ausgegeben
    - Der Funktion perror() kann auch ein NULL-Zeiger übergeben werden
    - Ausgabe: No such file or directory (ENOENT)

```c
#include <stdio.h>
#include <stdlib.h>

int main(void) {
   FILE *fp;

   fp = fopen("keinedatei.dat", "r");
   if (NULL == fp) {
      perror("Kann nicht aus Datei lesen ");
      return EXIT_FAILURE;
   }
   return EXIT_SUCCESS;
}
```

* `char *strerror(int error_nr);`
  - Die Funktion liefert als Rückgabewert einen Zeiger auf einen String, der zur Systemfehlermeldung der Variablen errno passt. Der Parameter error_nr beinhaltet in der Regel die Fehlervariable von errno.
  - Bsp:
    - Das Programm läuft genauso ab wie das Programm zuvor mit perror().

```c
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>

int main(void) {
   FILE *fp;

   fp = fopen("keinedatei.dat", "r");
   if (NULL == fp) {
      fprintf(stderr, "%s\n", strerror(errno));
      return EXIT_FAILURE;
   }
   return EXIT_SUCCESS;
}
```
