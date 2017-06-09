#include <unistd.h>
#include <stdio.h>

int main(int argc, char** argv) {
	int a = 5;
	int newPid;
	if ((newPid = fork()) == -1) {
		perror("Fork failed\n");
	}
	printf("Retour de fork : %d\n", newPid);
	if(newPid) {
		/* Processus Pere */
		int b =  a * 5;
		printf("Valeur de a : %d\nValeur de b : %d\n", a, b);
	} else {
		/* Processus Fils */
		int b = a * 2;
		printf("Valeur de a : %d\nValeur de b : %d\n", a, b);
	}
}
