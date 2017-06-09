#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <signal.h>
#include <errno.h>


void child_onSignal() {
	printf("Processus Pere\n");
	if(execl("/bin/ps", "YOLO\n", NULL) == -1) {
		perror("Erreur Exec\n");
		exit(23);
	}
}

int main(int argc, char** argv) {
	/*struct sigaction sa;
	sa.sa_handler = child_signal;*/
	int pidFils;
	/*sigaction(SIGCHLD, &sa, NULL);*/
	if((pidFils = fork()) == -1) {
		perror("Fork Failed\n");
		exit(13);
	}
	if(pidFils == 0) {
		/* Processus Fils */
		printf("Fils\n");
		if(execl("/bin/ps", "Zombie\n", NULL) == -1) {
			perror("Erreur Exec\n");
			exit(23);
		}
	} else {
		/* Processus Pere */
		if(signal(SIGCHLD, child_onSignal) == SIG_ERR) {
			perror("Error signal\n");
			exit(33);
		}
		pause();
	}
}
