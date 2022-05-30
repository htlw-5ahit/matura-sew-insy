//
// Created by Clemens Rumpfhuber on 03.06.20.
//

#include <stdio.h>
#include "article_list.h"

int main() {

    add_element("Radiergummi", 10004, 4);
    add_element("Bleistift", 1000, 3);
    add_element("Kugelschreiber", 10002, 3);
    add_element("Radiergummi", 10004, 4);

    print_articles(1);
    print_articles(0);

    printf("\n\ndelete items\n\n");

    printf("delete_element_by_name Bleistift: %s\n", delete_element_by_name("Bleistift") ? "Error!!" : "Finished");
    printf("delete_element_by_name Spitzer: %s\n", delete_element_by_name("Spitzer") ? "Error!!" : "Finished");
    printf("delete_element_by_id 10002: %s\n", delete_element_by_id(10002) ? "Error!!" : "Finished");
    printf("delete_element_by_id 1: %s\n\n", delete_element_by_id(1) ? "Error!!" : "Finished");

    print_articles(1);


    printf("\n\ntake items\n\n");

    printf("take_article_by_id 10: %s\n", take_article_by_id(10004, 10)  ? "Error!!" : "Finished");
    printf("take_article_by_id 10: %s\n\n", take_article_by_id(10004, 3)  ? "Error!!" : "Finished");

    print_articles(1);

    cleanup_array();
}