#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define TAILLE_BUFFER  12

int main(int argc, char** argv) {
	int compteur;
	char buffer[TAILLE_BUFFER];
	int ret;
	int nbrEcrit;
	for (compteur = 0; compteur < 10; compteur++) {
		ret=read(0, buffer, TAILLE_BUFFER);
		if (buffer[ret-1] != '\n') {
			perror("Petit filou\n");
			while(buffer[ret-1] != '\n') {
				ret = read(0, buffer, TAILLE_BUFFER);
			}
			continue;
		}
		if (ret == -1) {
			perror("Erreur lors de la lecture\n");
			exit(3);
		} 
		if((nbrEcrit = write(1, buffer, ret)) != ret) {
			perror("Erreur lors de l'écriture\n");
			exit(13); 
		}
	}
}
