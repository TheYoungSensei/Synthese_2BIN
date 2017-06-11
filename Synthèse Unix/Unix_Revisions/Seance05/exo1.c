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
	struct sigaction sa;
	sigset_t set;
	sa.sa_handler = child;
	sa.sa_flags = 0;
	SYS(sigemptyset(&sa.sa_mask));
	SYS(sigfillset(&set) == -1);
	SYS(sigdelset(&set, SIGCHLD));
	SYS(sigprocmask(SIG_BLOCK, &set, NULL));
	sigaction(SIGCHLD, &sa, NULL);
	if(pidFils == 0) {
		/* Processus Fils */
		SYS(execl("/bin/ps", "Fils\n", NULL));
		
	} else {
		sleep(2);
		sigsuspend(&set);
		/* Processus Père */
	}
	SYS(sigprocmask(SIG_UNBLOCK, &set, NULL));
}
