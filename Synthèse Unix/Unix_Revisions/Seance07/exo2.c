#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define NBMAX_CAR 256
#define SYS(call) ((call) == -1) ? perror(#call ": ERROR"), exit(1) : 0

int main(int argc, char **argv) {
	int fils;
	int pipefd[2];
	int pipefd2[2];
	int nbrLu;
	char buffer[256];
	char ligne[256];
	if(argc != 1) {
		SYS(write(2, "monProgramme\n", 12));
		exit(0);
	}
	SYS(pipe(pipefd2));
	SYS(pipe(pipefd));
	SYS(fils = fork());
	if(fils != 0) {
		/* Processus Père */
		SYS(close(pipefd[0]));
		SYS(close(pipefd2[1]));
		while(nbrLu = read(0, buffer, NBMAX_CAR)) {
			if(nbrLu == -1) {
				perror("write : ");
				exit(1);
			}
			if(buffer[nbrLu - 1] != '\n') {
				SYS(write(2, "Trop grande ligne entrée\n", 26));
				while(buffer[nbrLu - 1] != '\n') {
					nbrLu = read(0, buffer, NBMAX_CAR);
				}
				continue;
			}
			buffer[nbrLu - 1] = '\0';
			SYS(write(pipefd[1], buffer, nbrLu));
			SYS(nbrLu = read(pipefd2[0], buffer, NBMAX_CAR));
			nbrLu = sprintf(ligne, "reçu de mon fils : %s\n", buffer);
			SYS(write(1, ligne, nbrLu));
			
		}
		SYS(close(pipefd[1]));
		printf("Le père se termine\n"); 
	} else {
		int compteur = 0;
		/* Processus Fils */
		SYS(close(pipefd[1]));
		SYS(close(pipefd2[0]));
		SYS(dup2(pipefd[0], 0));
		SYS(dup2(pipefd2[1], 1));
		while(execl("/bin/cat", "cat", (char *) 0));
	}
}
