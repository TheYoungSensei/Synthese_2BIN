#include <unistd.h>
#include <stdlib.h>
#include <string.h>

#define TAILLE_BUFFER 81
int main(int argc, char** argv) {
	char * pret = "trois......deux......un..\n";
	char * partez;
	int pid;
	if(write(1, pret , strlen(pret)) == -1) {
		perror("Write Failed\n");
		exit(44);
	}
	if((pid = fork()) == -1) {
		perror("Fork Failed\n");
		exit(43);
	}
	if(pid == 0) {
		partez = "partez\n";
		if(write(1, partez, strlen(partez)) == -1) {
			perror("Write Failed\n");
			exit(44);
		}
	}
}
