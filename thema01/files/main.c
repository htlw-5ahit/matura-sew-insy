#include <stdio.h>
#include <string.h>

#define MAX_LINE 80

#define SUCCESS 0
#define ERROR_FILE_OPEN 1
#define ERROR_WRITE_FILE 2
#define ERROR_READ_FILE 3

typedef struct person {
    char firstName[MAX_LINE];
    char lastName[MAX_LINE];
    int yearOfBirth;
} person_t;

int writePerson(char *filename, person_t p) {
    FILE *fp;
    int errCode = SUCCESS;

    fp = fopen(filename,"a");

    if (fp != NULL) {
        printf("SCHREIBE Datei: %s\n", filename);
        if (fwrite(&p, sizeof(person_t), 1, fp) <= 0)
            errCode=ERROR_WRITE_FILE;
        fclose(fp);
    } else
        errCode = ERROR_FILE_OPEN;

    return errCode;
}

int writePersons(char* fileName, person_t *p, int count){
    FILE *fp;
    int errCode = SUCCESS;

    fp = fopen(fileName,"a");
    if (fp != NULL){
        printf("SCHREIBE DATEI: %s\n", fileName);

        if (fwrite(p, sizeof(person_t), count, fp) <= 0)
            errCode= ERROR_WRITE_FILE;
        fclose(fp);
    }
    else {
        errCode = ERROR_FILE_OPEN;
    }
    return errCode;
}

int readPersons(char *filename,int count){
    FILE *fp;
    int errCode = SUCCESS;
    person_t p[count];

    fp = fopen(filename,"r");
    if (fp!=NULL) {
        printf("Read Data in: %s\n\n", filename);
        if (fread(p, sizeof(person_t), count, fp) > 0){
            for (int i = 0; i < count; ++i) {
                printf("Vorname: %s  Nachname: %s - geb. am: %d \n", p[i].firstName,p[i].lastName,p[i].yearOfBirth);
            }
        } else errCode=ERROR_READ_FILE;
        fclose(fp);
    } else
        errCode = ERROR_FILE_OPEN;

    return errCode;
}

int writeTextFile(char *filename, char *text) {
    FILE *fp;
    int errCode = SUCCESS;

    fp = fopen(filename,"a");

    if (fp != NULL) {
        printf("SCHREIBE Datei: %s\n", filename);
        if (fputs(text,fp) <= 0)
            errCode=ERROR_WRITE_FILE;
        fclose(fp);
    } else
        errCode = ERROR_FILE_OPEN;

    return errCode;
}

int readTextFile(char *filename) {
    FILE *fp;
    int errCode = SUCCESS;
    char str[MAX_LINE];

    fp = fopen(filename,"r");

    if (fp != NULL) {
        while (fgets(str, MAX_LINE, fp) != NULL) {
            printf("%s", str);
        }
        fclose(fp);
    } else
        errCode = ERROR_FILE_OPEN;

    return errCode;
}

int main() {

    /*if(writeTextFile("text.txt","Hello World\n") != SUCCESS)
        printf("\nFehler beim Schreiben der Datei!\n");


    readTextFile("text.txt");*/

    person_t p[2];

    strcpy(p[0].lastName, "Thomas");
    strcpy(p[0].firstName, "Helml");
    p[0].yearOfBirth = 1975;


    strcpy(p[1].lastName, "Gams");
    strcpy(p[1].firstName, "Erich");
    p[1].yearOfBirth = 1971;

    writePersons("persons4.bin", p, 2);

    readPersons("persons4.bin", 2);

    return 0;
}
