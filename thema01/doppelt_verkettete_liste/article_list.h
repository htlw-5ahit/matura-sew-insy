//
// Created by Clemens Rumpfhuber on 03.06.20.
//

#ifndef INC_614_DOPPELT_VERKETTETE_LISTEN_ARTICLE_LIST_H
#define INC_614_DOPPELT_VERKETTETE_LISTEN_ARTICLE_LIST_H

#define CHAR_LEN 50

#define NO_ERROR 0;
#define ERR_MEM_ALLOCATION 100
#define ERR_MEM_ALLOCATION_FIRST_LAST 101
#define ERR_LIST_NOT_INITIALISED 102;
#define ERR_ITEM_NOT_FOUND 103;
#define ERR_ITEM_NOT_ENOUGH_AMOUNT 104;

typedef struct article {
    char name[CHAR_LEN];
    int id;
    int amount;
    struct article *next;
    struct article *prev;
} ARTICLE;

int add_element(char *name, int id, int amount);

void print_articles(int order);

int delete_element_by_name(char *name);

int delete_element_by_id(int id);

int take_article_by_id(int id, int amount);

void cleanup_array();

#endif //INC_614_DOPPELT_VERKETTETE_LISTEN_ARTICLE_LIST_H
