#include "livre.h"

int lireFichier(FILE * f, Noeud** biblio);
int main(int argc, char **argv) {
	Noeud* biblio = initPile();
	char * ligne;
	int nbreLivre = 0;
	Livre unLivre;
	FILE *fin = NULL;
	FILE *fout = NULL;
	if((ligne = (char*) malloc(sizeof(char) * 256)) == NULL) {
		perror("Erreur Malloc");
		exit(24);
	}
	switch(argc) {
		default : 
			printf("Usage : %s [fin] fout \n", argv[0]);
			exit(1);
		case 3 :
			if((fin = fopen(*(++argv), "r")) == NULL) {
				perror("Error Ouverture fin");
				exit(10);
			} else {
				nbreLivre = lireFichier(fin, &biblio);
			}	
		case 2 : 
			if((fout = fopen(*(++argv), "w")) == NULL) {
				perror("Error Ouverture fout");
				exit(11);
			}
			break;
	}
	printf("Veuilliez entrer un livre\n");
	while(fgets(ligne, 256, stdin) && ligne[0] != '\n') {
		unLivre = str2livre(ligne);
		printf("Ajout du Livre\n");
		nbreLivre = addLivre(&biblio, unLivre);
		printf("Veuilliez entrer a nouveau un livre\n");
	}
	afficherBib(biblio, nbreLivre);
	ecrireFichier(fout, biblio, nbreLivre);
	freeBiblio(&biblio);
}

int lireFichier(FILE * f, Noeud** biblio) {
	int nbrEnr = 0;
	Livre l;
	int taille = 0;
	char ligne[256];
	while((nbrEnr = fread(ligne, sizeof(Livre), 1, f)) == 1) {
		l = str2livre(ligne);
		taille = addLivre(biblio, l);
	}
	if((nbrEnr != 1) && feof(f)) {
		perror("Probleme de lecture");
		exit(12);
	}
	fclose(f);
	return taille;
}

int ecrireFichier(FILE *f, Noeud* bib, int n) {
	printf("Début de l'écriture\n");
	Noeud* ptr = bib;
	while(ptr != NULL) {
		fwrite(&(ptr->livre), sizeof(Livre), 1, f);
		ptr = ptr->noeudSuivant;
	}
	fclose(f);
	printf("Fin ecriture\n");
	return 1;
}
