#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <signal.h>

void child_signal(int sig) {
	if(execl("/bin/ps", "YOLO\n", NULL) == -1) {
		perror("Erreur Exec\n");
		exit(23);
	}
}

int main(int argc, char** argv) {
	int pidFils;
	if((pidFils = fork()) == -1) {
		perror("Fork Failed\n");
		exit(13);
	}
	if(pidFils == 0) {
		printf("Processus Fils\n");
		/* Processus Fils */
		if(execl("/bin/ps", "Zombie\n", NULL) == -1) {
			perror("Erreur Exec\n");
			exit(23);
		}
	} else {
		/* Processus Pere */
		struct sigaction sa;
		sigset_t set;
		sa.sa_handler = child_signal;
		/* Initialisation flags + mask */	
		sa.sa_flags = 0;
		sigemptyset(&sa.sa_mask);
		if (sigfillset(&set) == -1) {
			perror("Error Set\n");
			exit(43);
		}
		if(sigdelset(&set, SIGCHLD) == -1 ){
			perror("Error Set\n");
			exit(43);
		}
		if(sigprocmask(SIG_BLOCK, &set, NULL) == -1) {
			perror("Error Set\n");
			exit(43);
		}
		sigaction(SIGCHLD, &sa, NULL);
		sigsuspend(&set);
		//sleep(1);
		printf("Processus Père\n");
		if(sigprocmask(SIG_UNBLOCK, &set, NULL) == -1) {
			perror("Error Set\n");
			exit(43);
		}
		pause();
	}
}
