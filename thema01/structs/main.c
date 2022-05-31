#include <stdio.h>
#include <string.h>

struct index { // "struct index": datentyp
    int seite;
    char titel[30];
} lib;

int main() {
    //struct index lib; // "lib": variablenanmen

    struct index lib1 = {1, "Testbuch"};

    lib.seite = 23;
    // strings können nicht mit "=" zugewiesen werden!
    // wir müssen daher die Funktion strcpy (dest, src) verwenden
    strcpy(lib.titel, "C-Programmieren");

    printf("%d, %s\n", lib.seite, lib.titel);

    printf("Die struct index braucht %lu Bytes Speicher\n", sizeof(struct index));
    printf("Die Variable lib braucht %lu Bytes Speicher\n", sizeof(lib));
    return 0;
}
