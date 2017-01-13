#include <stdio.h>
#include <stdlib.h>

void imprimerTable(char* legende, int* tableEntier, int tailleLogigue);
int ajouterTable(int** tableEntier, int* tailleLogique, int valeurAjout);
int chargerTable(char** tableString, int** tableEntier);

int main(int argc, char** argv){
	int i;
	int* tableEntier;
	int tailleLogique = 0;
	tailleLogique = chargerTable(argv, &tableEntier);
	imprimerTable("Voici la table après l'ajout des arguments \n", tableEntier, tailleLogique);
	printf("%d", tailleLogique);
	 
}

int ajouterTable(int** tableEntier, int* tailleLogique, int valeurAjout) {
	static int taillePhysique = 3;
	if(*tailleLogique == 0){
		if((*tableEntier = (int*) malloc(sizeof(int) * taillePhysique)) == NULL) {
			perror("Erreur Malloc");
			return -1;
		}
	}
	if(taillePhysique == *tailleLogique){
		printf("check");
		taillePhysique *=2;
		if((*tableEntier = (int*) realloc(tableEntier, sizeof(int) * taillePhysique)) == NULL) {
			perror("Erreur Malloc");
			return -1;
		}
	}
	*tableEntier[*tailleLogique] = valeurAjout;
	*tailleLogique++;
	return *tailleLogique;
}

int chargerTable(char** tableString, int** tableEntier){
	char** iterateur;
	int compteur = 0;
	for(iterateur = tableString + 1; *iterateur != NULL; iterateur++) {
		compteur = ajouterTable(tableEntier, &compteur, atoi(*iterateur));
	}
	printf("%d", compteur);
	return compteur;
}

void imprimerTable(char* legende, int* tableEntier, int tailleLogique) {
	int* ptr1;
	printf("%s \n", legende);
	for(ptr1 = tableEntier; ptr1 - tableEntier < tailleLogique; ptr1++) {
		printf("%d, ", *ptr1);
	}
}
