#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "pile2.h"

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

