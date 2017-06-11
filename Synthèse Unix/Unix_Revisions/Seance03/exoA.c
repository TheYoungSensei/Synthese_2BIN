#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

#define SYS(call) ((call) == -1) ? perror(#call " : ERROR"), exit(1) : 0

int main(int argc, char **argv) {
	int a = 5;
	int b;
	int fils;
	SYS(fils = fork());
	printf("Fork : %d\n", fils);
	if(fils != 0) {
		/* Processus père */
		b = a * 5;	
	} else {
		/* Processus fils */
		b = a * 2;
	}
	printf("a : %d\n", a);
	printf("b : %d\n", b);
}
