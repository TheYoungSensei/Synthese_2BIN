#include "pile.h"

pile initPile(){
	pile pileEntier;
	pileEntier.noeudRacine = NULL;
	return pileEntier;
}

int push(pile * pileEntier, int entier) {
	noeud* noeudAInserer;
	if((noeudAInserer = (noeud*) malloc(sizeof(noeud))) == NULL) {
		perror("Erreur Malloc");
		return -1;
	}
	noeudAInserer->entier = entier;
	noeudAInserer->noeudSuivant = pileEntier->noeudRacine;
	pileEntier->noeudRacine = noeudAInserer;
	return entier;
}

int pop(pile * pileEntier) {
	if(pileVide(*pileEntier) == 0) {
		fprintf(stderr, "Erreur depilage de pile vide ! \n");
		exit(2);
	}
	noeud* noeudASupp = pileEntier->noeudRacine;
	pileEntier->noeudRacine = noeudASupp->noeudSuivant;
	return noeudASupp->entier;
}

int pileVide(pile pileEntier) {
	if(pileEntier.noeudRacine == NULL) {
		return 0;
	} else {
		return 1;
	}
}


