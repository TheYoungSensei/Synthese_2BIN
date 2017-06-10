#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#define NBMAX_CAR 80
#define SYS(call) ((call) == -1) ? perror(#call ": ERROR"), exit(1) : 0

int main(int argc, char **argv) {
	char buffer[NBMAX_CAR];
	int nbLu;
	int maxFile;
	int minFile;
	if(argc != 3) {
		perror("monProgramme fichier1 fichier2\n");
		exit(1);
	}
	SYS(minFile = open(*++argv, O_CREAT|O_TRUNC|O_WRONLY));
	SYS(maxFile = open(*++argv, O_CREAT|O_TRUNC|O_WRONLY)); 
	while(1) {
		SYS(nbLu = read(0, buffer, NBMAX_CAR));
		if(buffer[nbLu - 1] != '\n') {
			SYS(write(1, "Petit Filou\n",12)); 
			while(buffer[nbLu - 1] != '\n') {
				nbLu = read(0, buffer, NBMAX_CAR);

			}
			continue;
		}
		if(!isalpha(buffer[0])) {
			perror("Caractère non alphabétique\n");
			continue;
		}
		if(islower(buffer[0])) {
			if(write(minFile, buffer, nbLu) != nbLu) {
				perror("Erreur lors de l'écriture\n");
				exit(2);
			}
		} else {
			if(write(maxFile, buffer, nbLu) != nbLu) {
				perror("Erreur lors de l'écriture\n");
				exit(2);
			}
		}
	}
	SYS(close(minFile));
	SYS(close(maxFile));

}
