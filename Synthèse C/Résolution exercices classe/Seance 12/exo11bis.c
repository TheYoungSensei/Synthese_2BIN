#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void imprimerTable(char* legende, int* tableEntier, int tailleLogique);
int ajouterTable(int** tableEntier, int* tailleLogique, int valeurAjout);
int chargerTable(char** tableString, int** tableEntier);
typedef int (* fctcmp)(const void *, const void *);
static int comparerInt(const void *elem1, const void *elem2);
int str2int(char* ligne, int *nombreConverti);

int main(int argc, char** argv) {
	int* tableEntier;
	int tailleLogique = 0;
	char* ligne;
	int buffer;
	if(argc > 1) {
		tailleLogique = chargerTable(argv+1, &tableEntier);
	}
	if((ligne = (char*) malloc(sizeof(char) * 25)) == NULL) {
		perror("Erreur Malloc");
		return -1;
	}
	printf("%d", tailleLogique);
	imprimerTable("Voici la table après y avoir ajoute les arguments", tableEntier, tailleLogique);
	printf("Veuillez si vous le souhaitez entrer un nombre \n");
	while(fgets(ligne, 25, stdin) != NULL && ligne[0] != '\n') {
		str2int(ligne, &buffer);
		if(buffer < 0) {
			printf("Erreur str2int %d", buffer);
			return -4;
		}
		ajouterTable(&tableEntier, &tailleLogique, buffer);
		printf("Veuillez si vous le souhaitez entrer un nombre \n");
	}
	imprimerTable("Voici la table après avoir lu sur stdin", tableEntier, tailleLogique);
	qsort(tableEntier, tailleLogique, sizeof(int),  (fctcmp) comparerInt);
	imprimerTable("Voici la table après l'avoir triée", tableEntier, tailleLogique);
}

int chargerTable(char** tableString, int** tableEntier) {
	char** iterateur;
	int compteur = 0;
	for(iterateur = tableString; *iterateur != NULL; iterateur++) {
		ajouterTable(tableEntier, &compteur, atoi(*iterateur));
	}
	return compteur;
}

int ajouterTable(int** tableEntier, int* tailleLogique, int valeurAjout) {
	static int taillePhysique;
	if(*tailleLogique == 0) {
		if((*tableEntier = (int*) malloc(sizeof(int) * 3)) == NULL) {
			perror("Erreur Malloc");
			return -1;
		}
		taillePhysique = 3;
	}
	if(taillePhysique == (*tailleLogique + 1)) {
		taillePhysique *= 2;
		if((*tableEntier = (int*) realloc(*tableEntier, sizeof(int) * taillePhysique)) == NULL) {
			perror("Erreur Malloc");
			return -1;
		}
	}
	(*tableEntier)[*tailleLogique] = valeurAjout;
	(*tailleLogique)++;
	return *tailleLogique;
}

void imprimerTable(char* legende, int* tableEntier, int tailleLogique) {
	int* iterateur;	
	printf("%s \n", legende);
	for(iterateur = tableEntier; iterateur - tableEntier < tailleLogique; iterateur++) {
		printf("%d, ", *iterateur);
	}
	printf("\n");
}

static int comparerInt(const void *elem1, const void *elem2) {
	return ((* (int  const *) elem1) - (* (int const *) elem2));
}

int str2int(char *ligne, int *nombreConverti) {
	char* valeurOriginelle;
	if(ligne[strlen(ligne) - 1] != '\n') {
		return -1;
	}
	if(ligne[strlen(ligne)] != '\0') {
		return -2;
	}
	if(ligne[0] == '\n') {
		return -3;
	}
	*nombreConverti = strtol(ligne, &valeurOriginelle, 0);
	return 0;
}
