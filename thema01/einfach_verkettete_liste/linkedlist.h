//
// Created by Clemens Rumpfhuber on 12.05.20.
//

#ifndef INC_613_EINFACH_VERKETTETE_LISTE_LINKEDLIST_H
#define INC_613_EINFACH_VERKETTETE_LISTE_LINKEDLIST_H

#define MAX_LEN 50

#define NO_ERROR 0
#define ERR_MEM_ALLOCATION -1

typedef struct data{
    char firstName[MAX_LEN];
    char lastName[MAX_LEN];
    struct data *next;
}DATA;

int addElementEnd (char *firstName, char *lastName);
int addElementBegin (char *firstName, char *lastName);
int addElementSorted (char *firstName, char *lastName);
int deleteElement (char *lastName);
void printAll();
void freeAll();

#endif //INC_613_EINFACH_VERKETTETE_LISTE_LINKEDLIST_H
