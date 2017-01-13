#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv){
	
	int m; /*Nomre de carac*/
	int n; /*Nombre de mots*/
	int estPresent;
	char **tabptr;
	char *ligne;
	char **iterateur;
	int compteur;
	int i = 0;
	
	if(argc < 3) {
		printf("Trop peu d'arguments\n");
		return 1;
	}
	m = atoi(*++argv)+2; /* (**(argv + 1) - '0') + 2;*/
	n = atoi(*++argv)+1; /* (**(argv + 2) - '0');*/
	if((ligne = (char*) malloc(sizeof(char) * m)) == NULL) {
		perror("Erreur malloc \n");
		return 1;
	}
	if((tabptr = (char**) malloc(sizeof(char*) * n)) == NULL) {
		perror("Erreur malloc \n");
		return 1;
	}
	printf("Veuillez entrer une phrase \n");
	while(fgets(ligne, m, stdin) && i < n) {
		if(ligne[0] == '\n'){
			printf("Fin de l'application \n");
			break;
		}
		if(ligne[strlen(ligne) - 1] != '\n'){
			printf("Trop grande phrase entree, faites attention la prochaine fois \n");
			while(fgets(ligne, m, stdin)){
				if(ligne[strlen(ligne) - 1] == '\n') {
					break;
				}
			}
			continue;
		}
		if((tabptr[i] = (char*) malloc(sizeof(char) * strlen(ligne) + 1)) == NULL){
			perror("Erreur malloc \n");
			return 1;
		}
		strcpy(tabptr[i], ligne);
		printf("%s", tabptr[i]);
		estPresent = 1;
		compteur = 0;
		for(iterateur = tabptr; *iterateur != NULL; iterateur++) {
			if(strcmp(*iterateur, ligne) == 0) {
				estPresent = 0;
				break;
			} else {
				compteur++;
			}
		}
		if(estPresent == 0) {
			printf("est presente \n");
		} else {
			printf("est absente \n");
		}
		printf("Neanmoins la chaine n'a pas ete trouvee %d fois\n", compteur); 
		i++;
		printf("Veuillez entrer une phrase \n");
	}
}
		
	
	
