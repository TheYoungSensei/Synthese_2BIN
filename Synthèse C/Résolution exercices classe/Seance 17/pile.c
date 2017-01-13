#include "pile.h"

pile initpile() {
	pile pileEntier;
	pileEntier.noeudRacine = NULL;
	return pileEntier;
}

int push(pile* pileEntier, int entier) {
	noeud *nouveauNoeud;
	if((nouveauNoeud = (noeud*) malloc(sizeof(noeud))) == NULL) {
		perror("Erreur Malloc");
		return -1;
	}
	nouveauNoeud->entier = entier;
	nouveauNoeud->noeudSuivant = pileEntier->noeudRacine;
	pileEntier->noeudRacine = nouveauNoeud;
	return entier;
}

int pop(pile* pileEntier) {
	noeud* noeudASupp = pileEntier->noeudRacine;
	pileEntier->noeudRacine = noeudASupp->noeudSuivant;
	return noeudASupp->entier;
}

int pileVide(pile pileEntier) {
	if(pileEntier.noeudRacine  == NULL) {
		return 0;
	} else {
		return 1;
	}
}

pile* reinitPile(pile* pileEntier) {
	pileEntier->noeudRacine = NULL;
	return pileEntier;
}
