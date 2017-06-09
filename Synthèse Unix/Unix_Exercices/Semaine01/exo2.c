#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <ctype.h>
#include <stdlib.h>

#define TAILLE_BUFFER 81

int main(int argc, char** argv) {
	int nbrLu;
	int nbrEcrit;
	char buffer[TAILLE_BUFFER];
	int fichier1;
	int fichier2;
	int fichier3;
	int fichier4;
	int flags = O_CREAT|O_TRUNC|O_WRONLY;
	
	if (argc != 3) {
		perror("monProgramme fichier1 fichier2\n");
		exit(3);
	}
	if((fichier2 = open(*++argv, flags)) == -1) {
		perror("Erreur lors de l'ouverture de fichier 3\n");
		exit(13);
	}
	if((fichier1 = open(*++argv, flags)) == -1) {
		perror("Erreur lors de l'ouverture de fichier 1\n");
		exit(13);
	} 
	while(nbrLu = read(0, buffer, TAILLE_BUFFER)) {
		if(buffer[nbrLu - 1] != '\n') {
			perror("Trop grande ligne entrée\n");
			while(buffer[nbrLu - 1] != '\n') {
				nbrLu = read(0, buffer, TAILLE_BUFFER);
			}
			continue;
		}
		if(!isalpha(buffer[0])) {
			perror("La ligne n'est pas valide\n");
			continue;
		}
		if(islower(buffer[0]) != 0) {
			if((nbrEcrit = write(fichier2, buffer, nbrLu)) != nbrLu) {				
				perror("Erreur lors de l'écriture\n");
				exit(23);
			}
		} else {
			if((nbrEcrit = write(fichier1, buffer, nbrLu)) != nbrLu) {
				perror("Erreur lors de l'écriture\n");
				exit(23);
			}
		}		
	}
	if(close(fichier1) == -1) {
		perror("Erreur lors de la fermeture\n");
		exit(33);
	}
	if(close(fichier2) == -1) {
		perror("Erreur lors de la fermeture\n");
		exit(33);
	}
}
