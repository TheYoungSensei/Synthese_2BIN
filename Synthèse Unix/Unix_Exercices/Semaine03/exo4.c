#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#define TAILLE_BUFFER 81
int main(int argc, char** argv) {
	int pid;
	printf("trois.......deux.....un..");
	if((pid = fork()) == -1) {
		perror("Fork Failed\n");
		exit(43);
	}
	if(pid == 0) {
		printf("partez");
	}
}
