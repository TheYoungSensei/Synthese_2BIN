#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <signal.h>
#include <errno.h>

#define SYS(call) ((call) == -1) ? perror(#call ": ERROR"), exit(1) : 0
#define SYSN(call) ((call) == NULL) ? perror(#call ": ERROR"), exit(1) : 0

void child() {
	SYS(execl("/bin/ps", "Père\n", NULL));
}

int main(int argc, char** argv) {
	int pidFils;
	SYS(pidFils = fork());
	if(pidFils == 0) {
		/* Processus Fils */
		SYS(execl("/bin/ps", "Fils\n", NULL));
		
	} else {
		/* Processus Père */
		if(signal(SIGCHLD, child) == SIG_ERR) {
			perror("Erreur signal\n");
			exit(1);
		}
		pause();
	}
}
