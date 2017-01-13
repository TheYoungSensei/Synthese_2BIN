#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define TITRE 0
#define AUTEUR 1
#define ISBN 2
#define EDITEUR 3
#define ANNEE 4
#define GENRE 5

enum Genre {BD, PO, TH, RO, RH, LF, LE, SC, IF, SF, SA, HI };

typedef struct Livre {
	char titre[128];
	char auteur[80];
	int isbn;
	char editeur[50];
	int anneeEdition;
	enum Genre genre;
} Livre;

typedef struct Noeud {
	Livre livre;
	struct Noeud * noeudSuivant;
} Noeud;

typedef int (*fctcmp)(const void*, const void*);

Livre str2livre(char*);
char* livre2str(Livre);
int addLivre(Noeud**, Livre);
int comparerLivre(Livre*, Livre*);
void afficherBib(Noeud*, int);
void freeBiblio(Noeud**);
Noeud* initPile();
