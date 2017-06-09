#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char** argv) {
	int newPid;
	char * pere = " 1 2 3\n";
	char * fils = " 4 5 6\n";
	char pidFils[256];
	int status;
	int pidWait;
	if ((newPid = fork()) == -1) {
		perror("Fork failed\n");
		exit(43);
	}
	if(newPid) {
		if((pidWait = wait(&status)) == -1) {
			perror("Wait Error\n");
			exit(45);
		}
		if(pidWait != newPid) {
			perror("This is not my son\n");
			exit(46);
		}
		/* Processus Pere */
		if(write(1, pere, strlen(pere)) == -1) {
			perror("Write failed\n");
			exit(44);
		}
		sprintf(pidFils, "Mon fils s'est termine avec : %d \n", WEXITSTATUS(status));
		if(write(1, pidFils, strlen(pidFils)) == -1) {
			perror("Write Failed\n");
			exit(44);
		}
	} else {
		/* Processus Fils */
		if(write(1, fils, strlen(fils)) == -1) {
			perror("Write failed\n");
			exit(44);
		}
		exit(44);
	}
}
