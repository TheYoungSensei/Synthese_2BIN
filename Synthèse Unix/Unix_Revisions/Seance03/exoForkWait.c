#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <sys/wait.h>
#include <sys/types.h>

#define SYS(call) ((call) == -1) ? perror(#call " : ERROR"), exit(1) : 0

int main(int argc, char **argv) {
	int fils;
	SYS(fils = fork());
	if(fils != 0) {
		int pid;
		char ligne[256];
		int statut;
		/* Processus Père */
		while((pid = wait(&statut)) != fils && pid != -1);
		if(pid == -1) {
			perror("wait : ERROR");
			exit(1);
		}
		sprintf(ligne, "Code fils : %d\n", WEXITSTATUS(statut));
		SYS(write(1, ligne, strlen(ligne)));
		SYS(write(1, "123\n", 4));
	} else {
		/* Processus Fils */
		SYS(write(1, "456\n", 4));
		exit(0);
	}
}
