#include <unistd.h>
#include <stdlib.h>


#define SYS(call) ((call) == -1) ? perror(#call " : ERROR"), exit(1) : 0

int main(int argc, char **argv) {
	int fils;
	SYS(write(1, "trois.. deux.. un.. \n", 21));
	SYS(fils = fork());
	if(fils == 0) {
		SYS(write(1, "partez\n", 7));
	}
}
