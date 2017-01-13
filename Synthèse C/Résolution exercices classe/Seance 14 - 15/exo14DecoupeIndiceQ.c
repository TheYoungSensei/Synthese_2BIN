#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void imprimerTable(char ** table, int nombreElem);
void trierTable(char** table, int nombreElem);
void toMin(char** table, int nombreElem);
typedef int (*fctcmp)(const void*, const void*);
static int comparerLigne(const void *elem1, const void *elem2);

int main(int argc, char** argv) {
	int nombreMaxMots; //taille physique
	int compteurMots; //taille logique
	char* ligne;
	char** table;
	char* token;
	char* iterateur;
	const char* delim = " \t\n\v\f\r";
	if(argc == 1) {
		nombreMaxMots = 50;
	} else if (argc == 2) {
		nombreMaxMots = atoi(argv[1]);
	} else {
		printf("decoupePhraseTrie [n]\n");
		return - 1;
	}
	if((ligne = (char*) malloc(sizeof(char) * 255)) == NULL) {
		printf("Erreur Malloc\n");
		return -2;
	}
	if((token = (char*) malloc(sizeof(char) * 255)) == NULL) {
		printf("Erreur Malloc\n");
		return -2;
	}
	if((table = (char**) malloc(sizeof(char*) * nombreMaxMots)) == NULL) {
		printf("Erreur Malloc\n");
		return -2;
	}
	compteurMots = 0;
	while(fgets(ligne, 255, stdin) != NULL && compteurMots < nombreMaxMots && ligne[0] != '\n') {
		token = strtok(ligne, delim);
		while(token != NULL) {
			if(strlen(token) < 2){
				token = strtok(NULL, delim);
				continue;
			}
			if((table[compteurMots] = (char*) malloc(sizeof(char) * strlen(token))) == NULL) {
				printf("Erreur Malloc");
				return -2;
			}
			if(compteurMots < nombreMaxMots) {
				strcpy(table[compteurMots],token);
				compteurMots++;
				token = strtok(NULL, delim);
			} else {
				printf("Table remplie \n");
				break;
			}
		}
	}
	printf("Debut du tri \n");
	toMin(table, compteurMots);
	qsort(table, compteurMots, sizeof(char*), (fctcmp) comparerLigne);
	printf("Fin du tri \n");
	imprimerTable(table, compteurMots);
}

void imprimerTable(char ** table, int nombreElem) {
	printf("Affichage de la table \n");
	int i;
	for(i = 0; i < nombreElem; i++) {
		/*printf("%s \n", table[i]);*/
	}
}

void toMin(char** table, int nombreElem) {
	int i;
	int j;
	for(i  = 0; i < nombreElem; i++) {
		for(j = 0; j < strlen(table[i]); j++){
			table[i][j] = tolower(table[i][j]);
		}
	}
} 


void trierTable(char** table, int nombreElem) {
	int i;
	int j;
	char *elem;
	for(i = 0; i < nombreElem; i++) {
		elem = table[i];
		for(j = i; j > 0 && strcmp(table[j - 1], elem) > 0; j--){
			table[j] = table[j - 1];
		}
		table[j] = elem;
	}
}


static int comparerLigne(const void *elem1, const void *elem2) {
	return strcmp((char const *) elem1, (char const *) elem2);
}
