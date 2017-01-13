#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#if !defined(_PILE_H_)

#define _PILE_H_

typedef struct pile { 
	int* tableEntier;
	int tailleLogique;
	int taillePhysique;
} pile;

pile initpile();

int push(pile* , int);

int pop(pile*);

int pilevide(pile);
	
pile* reinitPile(pile*);

#endif
