#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct noeud {
	int entier;
	struct noeud *noeudSuivant;
} noeud;

typedef struct pile {
	noeud* noeudRacine;
} pile;

pile initpile();
int push(pile* pileEntier, int entier);
int pop(pile * pileEntier);
int pilevide(pile pileEntier);

int main(int argc, char** argv) {
	pile pileEntier = initpile();
	char* ligne;
	char arithm[] = {'0','1','2','3','4','5','6','7','8','9','+','-','*','/', ' '};
	char* token;
	char delim[] = {' '};
	if((ligne = (char*) malloc(sizeof(char) * 256)) == NULL) {
		perror("Erreur Malloc");
		return -1;
	}
	while(fgets(ligne,256,stdin) != NULL && ligne[0] != '\n') {
		if(strspn(ligne, arithm) != (strlen(ligne) - 1)){
			printf("Attention Opération Arithmétique non correcte\n");
			continue;
		}
		token = strtok(ligne, delim);
		if(token[0] == '+' || token[0] == '-' || token[0] == '*' || token[0] == '/') {
			printf("Erreur lors du premier caractère entré .... \n");
			continue;
		} else {
			push(&pileEntier, atoi(token));
		}
		while((token = strtok(NULL, delim)) != NULL) {
			if(token[0] == '+' || token[0] == '-' || token[0] == '*' || token[0] == '/') {
				if(pileVide(pileEntier) == 0) {
					printf("Opération Arithmétique impossible (premier entier absent)\n");
					break;
				}
				int entier1 = pop(&pileEntier);
				if(pileVide(pileEntier) == 0) {
					printf("Opération Arithmétique impossible (deuxieme entier absent)\n");
					break;
				}
				int entier2 = pop(&pileEntier);
				switch(token[0]) {
					case '+' : 
						push(&pileEntier, entier1 + entier2);
						printf("%d + %d = %d\n", entier1, entier2, entier1 + entier2);
						break;
					case '-' :
						push(&pileEntier, entier1 - entier2);
						printf("%d - %d = %d\n", entier1, entier2, entier1 - entier2);
						break;
					case '*' :
						push(&pileEntier, entier1 * entier2);
						printf("%d * %d = %d\n", entier1, entier2, entier1 * entier2);
						break;
					case '/' : 
						push(&pileEntier, entier1 / entier2);
						printf("%d / %d = %d\n", entier1, entier2, entier1 / entier2);
						break;
				}
			} else {
				push(&pileEntier, atoi(token));
			}
		}
	}
}

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
