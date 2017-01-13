/***********************************************************************
 * biblio.c
 * bdh 
 * Dimanche, 20 nov 2016 
 ***********************************************************************/
#include "livre.h"

int lireFichier(FILE *, Noeud**);
int ecrireFichier(FILE *, Noeud*, int );

int main (int argc, char **argv){
	Noeud* maBib;	
	char ligne[257];
	Livre unLivre;
	int nbreLivre = 0;
	FILE *fin = NULL;
	FILE *fout = NULL;
	maBib = initPile();
	/* usage et gestion des fihciers*/
	switch(argc) {
		default : fprintf(stderr, "Usage : %s [fin] fout \n", *argv);
			  exit(1);
		case 3 : if ((fin = fopen(*++argv, "r")) == NULL){
				 perror("ouverture de fin");
				 exit(10);
			 } else {
				 nbreLivre = lireFichier(fin, &maBib);
			 }
		case 2 : if ((fout = fopen(*++argv, "w")) == NULL){
				 perror("ouverture de fout");
				 exit(11);
			 }
			 break;
	}

	while (fgets(ligne, 256, stdin) && ligne[0] != '\n'){
		unLivre = str2Livre(ligne);
		nbreLivre = addLivre(&maBib, unLivre);
	}
	afficherBib(maBib, nbreLivre);
	/*qsort(maBib, nbreLivre, sizeof(Livre), (fctcmp)comparerLivre);*/
	/*afficherBib(maBib, nbreLivre);*/
	ecrireFichier(fout, maBib, nbreLivre);
	freeBiblio(&maBib);
}

int lireFichier(FILE *f, Noeud** bib){
	Livre l;
	int taille;
	int nbrEnreg;
	char * ligne;

	while((nbrEnreg = fread(ligne, sizeof(Livre), 1, f)) == 1) {
		l = str2Livre(ligne);
		taille = addLivre(bib, l);
	}
	if((nbrEnreg != 1) && !feof(f)){
		perror("probleme de lecture");
		exit(12);
	}
	return taille;
}

int ecrireFichier(FILE *f, Noeud* bib, int n){
	Livre l;
	Noeud *ptr=bib;
	int i;
	char * ligne;

	for (i = 0; i < n; i++){
		if((fwrite(&(ptr->livre), sizeof(Livre), 1, f)) != 1) {
			perror("ecrire");
			exit(13);
		}
		ptr = ptr->noeudSuivant;
	}
	return 1;
}

