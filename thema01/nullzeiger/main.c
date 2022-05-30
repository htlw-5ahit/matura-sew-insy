#include <stdio.h>

/*int main() {
    // zeiger mit null initialisieren
    int *iptr1 = NULL;
    int *iptr2 = NULL;
    int ival1, ival2;

    // initialisierung: zeiger erhhält adresse von ival1
    iptr1 = &ival1;
    // ival1 erhält den wert 123
    *iptr1 = 123;

    iptr2 = &ival2;
    *iptr2 = 456;

    iptr2 = iptr1;
    *iptr2 = 456;

    printf("*iptr1: %d\n", *iptr1);
    printf("*iptr2: %d\n", *iptr2);
    printf("ival1: %d\n", ival1);
    printf("ival2: %d\n", ival2);
}*/

int strlength(char *str){
    char *p;
    p = str;
    while (*p!='\0') p++;
    return p-str;
}

int main() {
    char *str = "Das ist ein super String";

    printf("%d", strlength(str));

}

