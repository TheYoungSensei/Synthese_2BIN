#include "pile2.h"

pile initpile() {
	pile pileEntier;
	pileEntier.tailleLogique = 0;
	pileEntier.taillePhysique = 4;
	if((pileEntier.tableEntier = (int*) malloc(sizeof(int) * 4)) == NULL) {
		perror("Erreur malloc");
		return (pile) pileEntier;
	}
	return pileEntier;
}

int push(pile* pileEntier, int entier) {
	if(pileEntier->tailleLogique == pileEntier->taillePhysique) {
		pileEntier->taillePhysique = pileEntier->taillePhysique * 2;
		if((pileEntier->tableEntier = (int*) realloc(pileEntier->tableEntier, sizeof(int) * pileEntier->taillePhysique)) == NULL) {
			perror("Erreur malloc");
			return -1;
		}
	}
	pileEntier->tableEntier[pileEntier->tailleLogique] = entier;
	pileEntier->tailleLogique += 1;
	return entier;
}

int pop(pile* pileEntier) {
	int elem = pileEntier->tableEntier[pileEntier->tailleLogique - 1];
	pileEntier->tailleLogique -= 1;
	return elem;
}

int pileVide(pile pileEntier) {
	if(pileEntier.tailleLogique  == 0) {
		return 0;
	} else {
		return 1;
	}
}

pile* reinitPile(pile* pileEntier) {
	pileEntier->tailleLogique = 0;
	return pileEntier;
}
