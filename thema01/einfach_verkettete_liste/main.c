#include <stdio.h>
#include "linkedlist.h"

int main() {

    /*
    addElementBegin("Thomas", "Helml");
    addElementBegin("Erich", "Gams");
    addElementBegin("Franz", "Reitinger");
    */

    addElementSorted("Thomas", "Helml");
    addElementSorted("Erich", "Gams");
    addElementSorted("Franz", "Reitinger");


    printAll();

    printf("\n gelöschte elemente: %d", deleteElement("Reitinger"));

    freeAll();

    printAll();

    return 0;
}
