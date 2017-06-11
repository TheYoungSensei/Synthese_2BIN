#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

#define SYS(call) ((call) == -1) ? perror(#call " : ERROR"), exit(1) : 0

int main(int argc, char **argv) {
	int fils;
	SYS(printf("trois.. deux.. un.. \n"));
	fflush(stdout);
	SYS(fils = fork());
	if(fils == 0) {
		SYS(printf("partez\n"));
	}
}
