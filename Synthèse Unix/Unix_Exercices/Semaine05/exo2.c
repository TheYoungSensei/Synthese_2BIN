#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TAILLE_BUFFER 257

int main(int argc, char** argv) {
	int pidFils;
	int pipefd[2];
	int pipeEntree;
	int pipeSortie;
	char bufferE[256];
	char bufferS[256];
	char ligne[256];
	int nbrLu;
	if(argc != 1) {
		perror("exo1\n");
		exit(13);
	}
	if(pipe(pipefd) == -1) {
		perror("Erreur lors de la création de la pile\n");
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
			nbrLu = sprintf(ligne, "PERE : %s\n", bufferE);
			if(write(1, ligne, nbrLu) == -1) {
				perror("Erreur lors de l'écriture sur la sortie standard\n");
				exit(18);
			}	
		}		
	} else {
		/* Processus Fils */
		pipeSortie = pipefd[0];
		close(pipefd[1]);
		while(nbrLu = read(pipeSortie, bufferS, TAILLE_BUFFER)) {
			nbrLu = sprintf(ligne, "FILS : %s\n", bufferS);
			if(write(1, ligne, nbrLu) == -1) {
				perror("Erreur lors de l'écriture\n");
				exit(17);
			}
		}
	}
}
