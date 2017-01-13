/***********************************************************************
 * livre.h
 * bdh 
 * Ven  2 déc 2016 09:31:03 CET
 ***********************************************************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#ifndef _LISTE_H_
#define _LISTE_H_
#define KO -1

#define TITRE 0
#define AUTEUR 1
#define ISBN 2
#define EDITEUR 3
#define ANNEE 4
#define GENRE 5

typedef int (*fctcmp)(const void *, const void*);

enum Genre {BD, PO, TH, RO, RH, LF, LE, SC, IN, SF, SA, HI} ;

typedef struct {
	char titre[128];
	char auteur[80];
	long isbn;
	char editeur[50];
	int an;
	enum Genre genre;
} Livre;

typedef struct Noeud{
	Livre livre;
	struct Noeud * noeudSuivant;
} Noeud;

Livre str2Livre(char *);
char * livre2str(char *, Livre);
int addLivre( Noeud**, Livre);
void freeBiblio(Noeud **);
int comparerLivre(Livre*, Livre*);
void afficherBib(Noeud*, int);
enum Genre setGenre(char *);
char *getGenre(enum Genre);
Noeud* initPile();
static char *lesGenres[] ={"Bande dessinée", "Poésie", "Théâtre",
				"Roman", "Roman historique", "Littérature française",
				"Littérature étrangère", "Sciences", "Informatique",
				"Science-fiction", "Santé", "Histoire", NULL };
#endif

