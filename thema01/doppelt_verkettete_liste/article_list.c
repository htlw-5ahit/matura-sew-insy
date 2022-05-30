//
// Created by Clemens Rumpfhuber on 03.06.20.
//

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "article_list.h"

ARTICLE *first = NULL;
ARTICLE *last = NULL;

int add_element(char *name, int id, int amount) {
    int errorCode = NO_ERROR;

    ARTICLE *help = malloc(sizeof(ARTICLE));

    if (help != NULL) {
        strcpy(help->name, name);
        help->id = id;
        help->amount = amount;
        help->next = NULL;
        help->prev = NULL;

        if (first == NULL && last == NULL) { // malloc first and last & add the first element
            first = malloc(sizeof(ARTICLE));
            last = malloc(sizeof(ARTICLE));

            if (first != NULL || last != NULL) {
                first->prev = NULL;
                first->next = help;

                last->next = NULL;
                last->prev = help;

                help->prev = first;
                help->next = last;
            } else errorCode = ERR_MEM_ALLOCATION_FIRST_LAST;
        } else {
            ARTICLE *akt = first;
            ARTICLE *prev = first;

            while (akt != NULL && akt != last && strcmp(akt->name,name) <= 0) { // search for alphabetical position
                prev = akt;
                akt = akt->next;
            }

            if (prev->id == id) { // add amount to existing article
                prev->amount = prev->amount + amount;
            } else { // add new article to list*/
                akt->prev = help;
                prev->next = help;
                help->next = akt;
                help->prev = prev;
            }
        }
    } else errorCode = ERR_MEM_ALLOCATION;
    return errorCode;
}

void print_articles(int order) {
    ARTICLE *it = order ? first : last;
    if (it != NULL) { // check if list is initialised
        if (order)
            while ((it = it->next) != NULL && it != last) // go throw list without first and last entry
                printf("Article ID: %d\nArticle Name: %s\nAmount: %d\n\n", it->id, it->name, it->amount);
        else
            while ((it = it->prev) != NULL && it != first) // go throw list without first and last entry
                printf("Article ID: %d\nArticle Name: %s\nAmount: %d\n\n", it->id, it->name, it->amount);
    } else printf("No articles found!");
}

void cleanup_array() {
    ARTICLE *akt = first;
    ARTICLE *prev = first;

    while (akt != NULL) {
        akt = akt->next;
        free(prev);
        prev = NULL;
        prev = akt;
    }
    first = NULL;
    last = NULL;
}

int delete_element_by_name(char *name) {
    int errorCode = NO_ERROR;
    int deletedItems = 0;
    ARTICLE *akt;
    ARTICLE *prev;

    if (first != NULL){
        akt = first->next;
        prev = first;

        while (akt != NULL) {
            if (strcmp(name, akt->name) == 0) {
                prev->next = akt->next;
                free(akt);
                akt = prev->next;
                deletedItems++;
            } else {
                prev = akt;
                akt = akt->next;
            }
        }

    } else errorCode = ERR_LIST_NOT_INITIALISED;
    if (deletedItems == 0) errorCode = ERR_ITEM_NOT_FOUND;
    return errorCode;
}

int delete_element_by_id(int id) {
    int errorCode = NO_ERROR;
    int foundItem = 0;
    ARTICLE *akt;
    ARTICLE *prev;

    if (first != NULL) {
        akt = first->next;
        prev = first;

        while (akt != NULL && !foundItem) {
            if (akt->id == id) {
                prev->next = akt->next;
                free(akt);
                akt = prev->next;
                foundItem = 1;
            } else {
                prev = akt;
                akt = akt->next;
            }
        }
    } else errorCode = ERR_LIST_NOT_INITIALISED;
    if (!foundItem) errorCode = ERR_ITEM_NOT_FOUND;
    return errorCode;
}

int take_article_by_id(int id, int amount) {
    int errorCode = NO_ERROR;
    int foundItem = 0;
    ARTICLE *akt;
    ARTICLE *prev;

    if (first != NULL) {
        akt = first->next;
        prev = first;

        while (akt != NULL && !foundItem) {
            if (akt->id == id) {

                if (akt->amount < amount) {
                    errorCode = ERR_ITEM_NOT_ENOUGH_AMOUNT;
                } else {
                    akt->amount -= amount;
                }

                foundItem = 1;
            } else {
                prev = akt;
                akt = akt->next;
            }
        }
    } else errorCode = ERR_LIST_NOT_INITIALISED;
    if (!foundItem) errorCode = ERR_ITEM_NOT_FOUND;
    return errorCode;
}