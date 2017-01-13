#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void imprimerTable(char ** table, int nombreElem);
void trierTable(char** table, int nombreElem);
void toMin(char** table, int nombreElem);

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
	trierTable(table, compteurMots);
	imprimerTable(table, compteurMots);
	printf("Fin du tri \n");
}

void imprimerTable(char ** table, int nombreElem) {
	printf("Affichage de la table \n");
	int i;
	for(i = 0; i < nombreElem; i++) {
		/*printf("%s \n", table[i]);*/
	}
}

void toMin(char** table, int nombreElem) {
	char** iterateur;
	char * ligne;
	for(iterateur = table; iterateur - table < nombreElem; iterateur++) {
		for( ligne = *iterateur; ligne - *iterateur < strlen(*iterateur); ligne++){
			*ligne = tolower(*ligne);
		}
	}
} 


void trierTable(char** table, int nombreElem) {
	char **iterateur;
	char *elem;
	char **petitIterateur;
	for(iterateur = table + 1; iterateur - table < nombreElem; iterateur++){
		elem = *iterateur;
		petitIterateur = iterateur;
		while((petitIterateur > table) && strcmp(*(petitIterateur - 1), elem) > 0) {
			*petitIterateur = *(petitIterateur - 1);
			petitIterateur--;
		}
		*petitIterateur = elem;
	}
}
