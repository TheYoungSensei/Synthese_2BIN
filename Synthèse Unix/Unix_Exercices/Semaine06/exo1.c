#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TAILLE_BUFFER 257

int main(int argc, char** argv) {
	int compteur = 0;
	int pidFils;
	int pipefd[2];
	int pipefd2[2];
	int pipeEntree;
	int pipeEntree2;
	int pipeSortie;
	int pipeSortie2;
	char bufferE[256];
	char bufferS[256];
	char ligne[256];
	int nbrLu;
	int ancienLu;
	if(argc != 1) {
		perror("exo1\n");
		exit(13);
	}
	if(pipe(pipefd) == -1) {
		perror("Erreur lors de la création de la pile\n");
		exit(14);
	}
	if(pipe(pipefd2) == -1) {
		perror("Erreur lors de la deuxième création de la pile\n");
		exit(14);
	}
	if((pidFils = fork()) == -1) {
		perror("Erreur Fork\n");
		exit(15);
	}
	if(pidFils != 0) {
		/* Processus Père */
		pipeEntree = pipefd[1];
		close(pipefd[0]);
		pipeSortie2 = pipefd2[0];
		close(pipefd2[1]);
		while(nbrLu = read(0, bufferE, TAILLE_BUFFER)) {
			if(bufferE[nbrLu - 1] != '\n') {
				perror("Trop grande ligne entrée\n");
				while(bufferE[nbrLu - 1] != '\n') {
					nbrLu = read(0, bufferE, TAILLE_BUFFER);	
				}	
				continue;
			}
			bufferE[nbrLu - 1] = '\0';
			if(write(pipeEntree, bufferE, nbrLu) == -1) {
				perror("Erreur lors de l'écriture\n");
				exit(16);
			}
			if(nbrLu = read(pipeSortie2, bufferE, TAILLE_BUFFER)) {
				nbrLu = sprintf(ligne, "reçu de mon fils : %s\n", bufferE);
				if(write(1, ligne, nbrLu) == -1) {
					perror("Erreur reception\n");
					exit(20);
				}
			}	
		}		
	} else {
		/* Processus Fils */
		if(dup2(pipefd[0], 0) == -1) {
			perror("Error Redirection\n");
			exit(16);
		}
		if(dup2(pipefd2[1], 1) == -1) {
			perror("Error Redirection\n");
			exit(16);
		}
		pipeSortie = pipefd[0];
		close(pipefd[1]);
		pipeEntree2 = pipefd2[1];
		close(pipefd2[0]);
		while(ancienLu = read(pipeSortie, bufferS, TAILLE_BUFFER)) {
			compteur++;
			nbrLu = sprintf(ligne, "FILS : %s\n", bufferS);
			if(write(pipeEntree2, bufferS, ancienLu) == -1) {
				perror("Erreur ecriture pipe retour\n");
				exit(18);
			}
		} 
	}
	printf("le pere se termine\n");
}
