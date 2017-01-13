#include "livre.h"

static int nbreL = 0;

Livre str2livre(char* s) {
	Livre l;
	char * token;
	int i;
	token = strtok(s, ";");
	for(i = 0; token != NULL; i++){
		switch (i) {
			case TITRE :
				strcpy(l.titre, token);
				break;
			case AUTEUR :
				strcpy(l.auteur, token);
				break;
			case ISBN :
				l.isbn = atoi(token);
				break;
			case EDITEUR :
				strcpy(l.editeur, token);
				break;
			case ANNEE :
				l.anneeEdition = atoi(token);
				break;
			case GENRE :
				l.genre = atoi(token);
				break;
		}
		token = strtok(NULL, ";\n");
	}
	return l;
}

char * livre2str(Livre l) {
	char * s;
	if((s = (char*) malloc(sizeof(char) * 256)) == NULL) {
		perror("Erreur Malloc");
		exit(14);
	}
	sprintf(s, "%s %s %d %s %d %d" , l.titre, l.auteur, l.isbn, l.editeur, l.anneeEdition, l.genre);
	return s;
}

int addLivre(Noeud** bib, Livre l) {
	Noeud* noeudPrec;
	Noeud* noeudSuiv;
	Noeud * nouveauNoeud;
	if((nouveauNoeud = (Noeud*) malloc(sizeof(Noeud))) == NULL) {
		perror("Erreur Malloc");
		return -1;
	}
	nouveauNoeud->livre = l;
	if(nbreL != 0) {
		noeudPrec = (*bib);
		noeudSuiv = (*bib)->noeudSuivant;
		while(noeudSuiv != NULL) {
			if(comparerLivre(&(nouveauNoeud->livre), &(noeudSuiv->livre)) < 0) {
				break;
			}
			noeudSuiv = noeudSuiv->noeudSuivant;
			noeudPrec = noeudPrec->noeudSuivant;
		}
		nouveauNoeud->noeudSuivant = noeudSuiv;
		noeudPrec->noeudSuivant = nouveauNoeud;
	} else {
		nouveauNoeud->noeudSuivant = (*bib)->noeudSuivant;
		(*bib) = nouveauNoeud;
	}
	nbreL++;
	return nbreL;
}

int comparerLivre(Livre *a, Livre *b) {
	int auxComp;
	if(a->anneeEdition != b->anneeEdition) 
		return a->anneeEdition - b->anneeEdition;
	if(auxComp = strcmp(a->editeur, b->editeur))
		return auxComp;
	return strcmp(a->auteur, b->auteur);
}

void afficherBib(Noeud* biblio, int t) {
	Noeud* ptr = biblio;
	char * ligne;
	if((ligne = (char*) malloc(sizeof(char) * 256)) == NULL) {
		perror("Erreur Malloc");
		exit(14);
	}
	printf("Voici ma bib de %d livres\n", t);
	while(ptr != NULL) {
		printf("\t%s\n", livre2str(ptr->livre));
		ptr = ptr->noeudSuivant;	
	}
	printf("--------------------------\n");
	return;
}

void freeBiblio(Noeud** biblio) {
	free(*biblio);
	nbreL = 0;
}

Noeud* initPile() {
	Noeud* biblio;
	if((biblio = (Noeud*) malloc(sizeof(Noeud))) == NULL) {
		perror("Erreur Malloc");
		exit(14);
	}
	biblio->noeudSuivant = NULL;
	return biblio;
}
