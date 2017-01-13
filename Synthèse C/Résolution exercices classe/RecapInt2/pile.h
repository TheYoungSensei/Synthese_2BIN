#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#if !defined(_PILE_H_)

#define _PILE_H_

typedef struct noeud {
	int entier;
	struct noeud* noeudSuivant;
} noeud;

typedef struct pile {
	struct noeud* noeudRacine;
} pile;

pile initPile();
int push (pile *, int);
int pop(pile *);
int pileVide(pile);
pile reinitPile(pile *);

#endif
