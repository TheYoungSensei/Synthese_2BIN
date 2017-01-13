/***********************************************************************
 * livre.c
 * bdh 
 * Ven  2 déc 2016 09:33:31 CET
 ***********************************************************************/
#include "livre.h"

static int taille = 0;
static int nbreL = 0;

Livre str2Livre(char *s){
	Livre l;
	char * token;
	int i;

	for (i=0, token=strtok(s, ";"); token != NULL;  i++, token=strtok(NULL, ";\n")){
		switch (i){
		case TITRE:
			strcpy(l.titre, token);
			break;
		case AUTEUR:
			strcpy(l.auteur, token);
			break;
		case ISBN:
			l.isbn = strtol(token, NULL, 10);
			break;
		case EDITEUR:
			strcpy(l.editeur, token);
			break;
		case ANNEE:
			l.an = strtol(token, NULL, 10);
			break;
		case GENRE:
			l.genre = setGenre(token);
			break;
		}
	}
	return l;
}


char * livre2str(char *s, Livre l){
	sprintf(s, "%-50s %-20s %ld %-20s %d %s", l.titre, l.auteur, l.isbn, l.editeur, l.an, getGenre(l.genre));
	return s;
}

int addLivre(Noeud **bib, Livre l){
	Noeud* noeudPrec;
	Noeud* noeudSuiv;
	struct Noeud * nouveauNoeud;
	if((nouveauNoeud = (struct Noeud*) malloc(sizeof(struct Noeud))) == NULL) {
		perror("Erreur Malloc");
		return -1;
	}
	nouveauNoeud->livre = l;
	if((*bib)->noeudSuivant != NULL) {
		noeudPrec = (*bib);
		noeudSuiv = (*bib)->noeudSuivant;
		while(noeudSuiv != NULL) {
			if(comparerLivre(&(nouveauNoeud->livre), &(noeudSuiv->livre)) < 0) {
				nouveauNoeud->noeudSuivant = noeudSuiv;
				noeudPrec->noeudSuivant = nouveauNoeud;
			}
			noeudSuiv = noeudSuiv->noeudSuivant;
			noeudPrec = noeudPrec->noeudSuivant;
		}
	} else {
		nouveauNoeud->noeudSuivant = (*bib)->noeudSuivant;
		(*bib) = nouveauNoeud;
	}
	nbreL++;
	return nbreL;
}

int comparerLivre(Livre*a, Livre*b){
	int auxComp;
	if (a->an != b->an)
		return a->an - b->an;
	if (auxComp =strcmp(a->editeur,b->editeur))
		return auxComp;
	return strcmp(a->auteur,b->auteur);
}


void afficherBib(Noeud* bib, int t){
	Noeud *ptr=bib;
	char ligne[256];

	int i;
	printf("Voici ma bib de %d livres\n", t);
	for (i = 0; ptr != NULL; ){
		printf("\t%s\n", livre2str(ligne, (Livre) ptr->livre));
		ptr =  ptr->noeudSuivant;
	}
	printf("---------------------------\n");
	return;
}

enum Genre setGenre(char *s){
	enum Genre g = BD;
	char **ptr = NULL;

	for (ptr = lesGenres; *ptr != NULL; ptr++, g++){
		if (!strcmp(s, *ptr)){
			return g;
		}
	}
	return KO;
}

char *getGenre(enum Genre g){
	return lesGenres[g];
}


void freeBiblio(Noeud **bib){

	free(*bib);
	taille = 0;
	nbreL = 0;
	

}

Noeud* initPile(){
	Noeud* maBib;
	if((maBib = (Noeud*) malloc(sizeof(Noeud))) == NULL) {
		perror("Erreur Malloc");
		exit(14);
	}
	maBib->noeudSuivant = NULL;
	return maBib;
}
