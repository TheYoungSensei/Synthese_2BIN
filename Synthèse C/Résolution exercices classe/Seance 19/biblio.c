#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <regex.h>

typedef struct biblio {
	char titre[128];
	char auteurs[80];
	long isbn;
	char editeur[50];
	int anneeEdition;
	enum genre {bandeDessinee, poesie, theatre, roman, romanHistorique, litteratureFrancaise, litteratureEtrangere, sciences, informatique, scienceFiction, sante, histoire} genre;	
} biblio;
void trierBiblio(biblio** biblio, int tailleLogique);
void afficherBiblio(biblio** biblio, int tailleLogique);
int remplirTable(char * nomFichier, biblio*** bibliotheque, int* tailleLogique, int* taillePhysique);
int ajoutBiblio(char* ligne, biblio*** bibliotheque, int* tailleLogique, int* taillePhysique);
int sauvegarderFichier(char* nomFichier, biblio** bibliotheque, int tailleLogique);
typedef int (*fctcmp)(const void *, const void *);
static int comparerLivre(biblio* elem1, biblio* elem2); 

int main(int argc, char** argv) {
	char* ligne;
	int tailleLogique = 0;
	int taillePhysique = 2;
	biblio** bibliotheque;
	int position = 1;
	if((ligne = (char*) malloc(sizeof(char) * 256)) == NULL) {
		perror("Erreur Malloc");
		return 20;
	}
	if(argc < 2 || argc > 3) {
		perror("biblio [fichierIn] fichierOut \n");
		return 1;
	} 
	if(argc == 3) {
		if(remplirTable(argv[1], &bibliotheque, &tailleLogique, &taillePhysique) == -1){
			perror("Erreur avec la lecture du fichier \n");
			return 10;
		}
		afficherBiblio(bibliotheque, tailleLogique);
		position = 2;
	}
	printf("Veuilliez entrer les informations relatives au livre : (separe par des ;)\n");
	while(fgets(ligne, 256, stdin) != NULL && ligne[0] != '\n'){
		ajoutBiblio(ligne, &bibliotheque, &tailleLogique, &taillePhysique);
		afficherBiblio(bibliotheque, tailleLogique);
	}
	qsort(bibliotheque, tailleLogique, sizeof(biblio*), (fctcmp) comparerLivre);
	afficherBiblio(bibliotheque, tailleLogique);
	if(sauvegarderFichier(argv[position], bibliotheque, tailleLogique) == -1) {
		perror("Erreur lors de la sauvegarde du fichier \n");
		return 11;
	}
	
}

int remplirTable(char* nomFichier, biblio*** bibliotheque, int* tailleLogique, int* taillePhysique) {
	FILE *fichier = NULL;
	char *ligne;
	if((fichier = fopen(nomFichier,"r")) == NULL) {
		perror("Probleme avec le fichier \n");
		return 12;
	}
	if((ligne = (char*) malloc(sizeof(char) * 256)) == NULL) {
		perror("Erreur Malloc");
		return 20;
	}
	while(fgets(ligne,256,fichier)){
		ajoutBiblio(ligne, bibliotheque, tailleLogique, taillePhysique);	
	}
	fclose(fichier);
	return 0;
}

int sauvegarderFichier(char* nomFichier, biblio** bibliotheque, int tailleLogique) {
	FILE *fichier = NULL;
	if((fichier = fopen(nomFichier,"w")) == NULL) {
		perror("Probleme avec le fichier \n");
		return 13;
	}
	fwrite(bibliotheque,(sizeof(biblio) * tailleLogique), 1,fichier);
	fclose(fichier);
	return 0;
}

int ajoutBiblio(char* ligne, biblio*** bibliotheque, int* tailleLogique, int *taillePhysique) {
		int reti;
		regex_t regex;
		char* token;
		const char* delim = ";";
		/* Compile regular expression */
		reti = regcomp(&regex, "[[:print:]]{1,128};[[:print:]]{1,80};[[:digit:]]{1,9};[[:print:]]{1,50};[[:digit:]]{1,4};[[:digit:]]{1}", REG_NOSUB | REG_EXTENDED);
		if(reti) {
			printf("Erreur lors de la création de l'Expression régulière \n");
			exit(1);
		}
		reti = regexec(&regex, ligne, 0, NULL, 0);
		if(reti == REG_NOMATCH) {
			printf("La ligne ne correspondait pas à ce qui était demandé \n");
			return 50;
		}
		if(*tailleLogique == 0) {
			if((*bibliotheque = (biblio**) malloc(sizeof(biblio) * *taillePhysique)) == NULL) {
				perror("Erreur Malloc \n");
				return 20;
			}
		}
		if((*tailleLogique) == (*taillePhysique)) {
			(*taillePhysique) *= 2;
			if(((*bibliotheque) = (biblio**) realloc((*bibliotheque), sizeof(biblio) * *taillePhysique)) == NULL) {
				perror("Erreur Malloc \n");
				return 20;
			}
		}
		if(((*bibliotheque)[*tailleLogique] = (biblio*) malloc(sizeof(biblio))) == NULL) {
			perror("Erreur Malloc \n");
			return 20;
		}
		token = strtok(ligne,delim);
		strcpy((*bibliotheque)[(*tailleLogique)]->titre,token);
		strcpy((*bibliotheque)[(*tailleLogique)]->auteurs,strtok(NULL,delim));
		(*bibliotheque)[(*tailleLogique)]->isbn = atoi(strtok(NULL,delim));
		strcpy((*bibliotheque)[(*tailleLogique)]->editeur,strtok(NULL,delim));
		(*bibliotheque)[(*tailleLogique)]->anneeEdition = atoi(strtok(NULL,delim));
		(*bibliotheque)[(*tailleLogique)]->genre = atoi(strtok(NULL,delim));
		(*tailleLogique)++;
}

void afficherBiblio(biblio** bibliotheque, int tailleLogique) {
	int i;
	printf("Affichage de la table : \n");
	for(i = 0; i < tailleLogique; i++){
		printf("Titre : %s \n", bibliotheque[i]->titre);
		printf("Auteur : %s \n", bibliotheque[i]->auteurs);
		printf("ISBN : %ld \n", bibliotheque[i]->isbn);
		printf("Editeur : %s \n", bibliotheque[i]->editeur);
		printf("Annee Edition : %d\n", bibliotheque[i]->anneeEdition);
		printf("Genre : %d\n", bibliotheque[i]->genre);
		printf("\n");
	}
	printf("Fin Affichage \n");
}

void trierBiblio(biblio** bibliotheque, int tailleLogique) {
	int i;
	int j;
	biblio* elem;
	for(i = 0; i < tailleLogique; i++) {
		elem = bibliotheque[i];
		for(j = i; j > 0 && bibliotheque[j - 1]->anneeEdition > elem->anneeEdition; j--) {
			bibliotheque[j] = bibliotheque[j - 1];
		}
		bibliotheque[j] = elem;
	}
}

static int comparerLivre(biblio *elem1, biblio* elem2) {
	/*if(((elem1->anneeEdition) - (elem2->anneeEdition)) <= 0) {*/
		return elem1->anneeEdition - elem2->anneeEdition;
	/*} else if (strcmp((elem1->editeur),(elem2->editeur)) <= 0) {
		return strcmp(elem1->editeur, elem2->editeur);
	} else if (strcmp((elem1->auteurs),(elem2->auteurs)) <= 0) {
		return strcmp(elem1->auteurs, elem2->auteurs);
	} else {
		return 1;
	}*/
}
