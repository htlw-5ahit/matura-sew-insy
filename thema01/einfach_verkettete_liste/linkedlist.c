//
// Created by Clemens Rumpfhuber on 12.05.20.
//

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "linkedlist.h"

DATA *first = NULL;

int addElementBegin (char *firstName, char *lastName) {
    int errCode = NO_ERROR;

    // 0. first element anlegen, nur für variante b
    if (first == NULL)
        first = malloc(sizeof(DATA));

    // 1. speicher für element reservieren
    DATA *help = malloc(sizeof(DATA));

    // 2. überprüfe ob speicherreservierung geklappt hat
    if (help != NULL) {

        // 3. daten in struktur befüllen
        strcpy(help->firstName, firstName);
        strcpy(help->lastName, lastName);
        help->next = NULL;

        // variante a - erstes element wird zum speichern von daten verwendet
        /*
        // gibt es schon ein element?
        if (first == NULL) {
            first = help;
        }
        else {
            help->next = first;
            first = help;
        }*/

        // variante b - erstes element beinhaltet keine daten und wird nie gelöscht
        help->next = first->next;
        first->next = help;

    }
    else errCode = ERR_MEM_ALLOCATION; // fehler bei speicherreservierung

    return errCode;
}

int addElementEnd (char *firstName, char *lastName) {
    int errCode = NO_ERROR;

    // 0. first element anlegen, nur für variante b
    if (first == NULL)
        first = malloc(sizeof(DATA));

    DATA *akt = first;

    // 1. speicher für element reservieren
    DATA *help = malloc(sizeof(DATA));

    // 2. überprüfe ob speicherreservierung geklappt hat
    if (help != NULL) {

        // 3. daten in struktur befüllen
        strcpy(help->firstName, firstName);
        strcpy(help->lastName, lastName);
        help->next = NULL;

        // 4. navigiere durch verkettete liste bis ans ende
        while (akt->next != NULL) {
            akt = akt->next;
        }

        // 5. element einfügen
        akt->next = help;
    }
    else errCode = ERR_MEM_ALLOCATION; // fehler bei speicherreservierung

    return errCode;
}

int addElementSorted(char *firstName, char *lastName){
    int errCode = NO_ERROR;

    // 0. first element anlegen, nur für variante b
    if(first == NULL) {
        first = malloc(sizeof(DATA));
        first->next = NULL;
    }

    DATA *akt = first;
    DATA *prev = first;

    // 1. speicher für element reservieren
    DATA *help = malloc(sizeof(DATA));

    // 2. überprüfe ob speicherreservierung geklappt hat
    if (help!=NULL) {
        // 3. daten in struktur befüllen
        strcpy(help->firstName, firstName);
        strcpy(help->lastName, lastName);
        help->next = NULL;

        // 4. navigiere durch verkettete liste
        while (akt != NULL && strcmp(akt->lastName,lastName) < 0){
            prev = akt;
            akt = akt->next;
        }

        // 5. element einfügen
        help->next = akt;
        prev->next = help;
    }
    else errCode = ERR_MEM_ALLOCATION; // fehler bei speicherreservierung

    return errCode;
}

int deleteElement(char *lastName){
    int deleted = 0;
    DATA *akt;
    DATA *prev;

    if (first != NULL){
        akt = first->next;
        prev = first;

        while (akt != NULL) {
            if (strcmp(lastName, akt->lastName) == 0) {
                prev->next = akt->next;
                free(akt);
                akt = prev->next;
                deleted++;
            } else {
                prev = akt;
                akt = akt->next;
            }
        }

    }
    return deleted;
}

void printAll(){
    if (first != NULL) {
        DATA *akt = first->next; // wegen variante b

        while (akt != NULL){
            printf("Vorname: %s\t\tNachname: %s\n", akt->firstName, akt->lastName);
            akt = akt->next;
        }
    }
    else printf("\nListe leer!\n");
}

void freeAll() {
    DATA *akt = first;
    DATA *prev = first;

    while (akt != NULL) {
        akt = akt->next;
        free(prev);
        prev = NULL;
        prev = akt;
    }
    // wichtig
    first = NULL;
}
