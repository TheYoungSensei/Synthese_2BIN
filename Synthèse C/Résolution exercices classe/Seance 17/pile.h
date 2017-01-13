#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#if !defined(_PILE_H_)

#define _PILE_H_

typedef struct noeud {
	int entier;
	struct noeud *noeudSuivant;
} noeud;

typedef struct pile { 
	noeud* noeudRacine;
} pile;

pile initpile();

int push(pile* , int);

int pop(pile*);

int pilevide(pile);
	
pile* reinitPile(pile*);

#endif
