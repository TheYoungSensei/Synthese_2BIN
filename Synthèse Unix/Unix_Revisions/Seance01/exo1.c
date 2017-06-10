#include <unistd.h>
#include <stdlib.h>

#define NBMAX_CAR 10
#define SYS(call) ((call) == -1) ? perror(#call ": ERROR"), exit(1) : 0

int main(int argc, char **argv) {
	char buffer[NBMAX_CAR];
	int nbLu;
	while(1) {
		SYS(nbLu = read(0, buffer, NBMAX_CAR));
		if(buffer[nbLu - 1] != '\n') {
			SYS(write(1, "Petit Filou\n",12)); 
			while(buffer[nbLu - 1] != '\n') {
				nbLu = read(0, buffer, NBMAX_CAR);

			}
			continue;
		}
		SYS(write(1, buffer, nbLu));
	}

}
