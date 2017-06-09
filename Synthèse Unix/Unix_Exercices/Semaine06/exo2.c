#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TAILLE_BUFFER 257

int main(int argc, char** argv) {
	int pidFils;
	int pipefd[2];
	int pipefd2[2];
	char buffer[256];
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
		if(close(pipefd[0]) == -1) {
			perror("Erreur lors de la fermeture\n");
			exit(21);
		}
		if(close(pipefd2[1]) == -1) {
			perror("Erreur lors de la fermeture\n");
			exit(21);
		}
		while(nbrLu = read(0, buffer, TAILLE_BUFFER)) {
			if(buffer[nbrLu - 1] != '\n') {
				perror("Trop grande ligne entrée\n");
				while(buffer[nbrLu - 1] != '\n') {
					nbrLu = read(0, buffer, TAILLE_BUFFER);	
				}	
				continue;
			}
			buffer[nbrLu - 1] = '\0';
			if(write(pipefd[1], buffer, nbrLu) == -1) {
				perror("Erreur lors de l'écriture\n");
				exit(16);
			}
			if(nbrLu = read(pipefd2[1], buffer, TAILLE_BUFFER)) {
				nbrLu = sprintf(ligne, "reçu de mon fils : %s\n", buffer);
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
		if(close(pipefd[0]) == -1) {
			perror("Erreur lors de la fermeture\n");
			exit(21);
		}
		if(dup2(pipefd2[1], 1) == -1) {
			perror("Error Redirection\n");
			exit(16);
		}
		if(close(pipefd2[1]) == -1) {
			perror("Erreur lors de la fermeture\n");
			exit(21);
		}
		if(close(pipefd[1]) == -1) {
			perror("Erreur lors de la fermeture\n");
			exit(21);
		}
		if(close(pipefd2[0]) == -1) {
			perror("Erreur lors de la fermeture\n");
			exit(21);
		}
		if(execl("/bin/cat", "cat", (char*) 0) == -1) {
			perror("Erreur exec\n");
			exit(20);
		}
	}
	printf("le pere se termine\n");
}
