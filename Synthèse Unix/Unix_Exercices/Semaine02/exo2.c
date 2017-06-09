#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <ctype.h>
#include <stdlib.h>
#include <stdio.h> /*TO DELETE */
#include <inttypes.h>

#define TAILLE_BUFFER 256

int main(int argc, char** argv) {
	int nbrLu;
	int nbrEcrit;
	char buffer[TAILLE_BUFFER];
	int fichier1;
	int fichier2;
	int flags = O_CREAT|O_TRUNC|O_WRONLY;
	struct stat* buf;
	int test;
	char userR;
	char userW;
	char userX;
	char groupR;
	char groupW;
	char groupX;
	char otherR;
	char otherW;
	char otherX;
	char * ligne;
	if (argc != 3) {
		perror("monProgramme fichier1 fichier2\n");
		exit(3);
	}
	if((ligne = (char*) malloc(sizeof(char) * 256)) == NULL) {
		perror("Erreur Malloc\n");
		exit(22);
	}
	test = fstat(0, buf);
	if (S_ISCHR(buf->st_mode) == 0) {
		perror("L'entrée n'est pas l'entrée standard\n");
		exit(33);
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
	fstat(fichier1, buf);
	sprintf(ligne, "%s a une taille de %zu et possede les permissions : \n", *argv, buf->st_size);
	write(1, ligne, TAILLE_BUFFER);
	if (buf->st_mode & S_IRUSR) {
		userR = 'r';
	} else {
		userR = '-';
	}
	if (buf->st_mode & S_IWUSR) {
		userW = 'w';
	} else {
		userW = '-';
	}
	if(buf->st_mode & S_IXUSR) {
		userX = 'x';
	} else {
		userX = '-';
	}
	if(buf->st_mode & S_IRGRP) {
		groupR = 'r';
	} else {
		groupR = '-';
	}
	if(buf->st_mode & S_IWGRP) {
		groupW = 'w';
	} else {
		groupW= '-';
	}
	if(buf->st_mode & S_IXGRP) {
		groupX = 'x';
	} else {
		groupX = '-';
	}
	if(buf->st_mode & S_IROTH) {
		otherR = 'r';
	} else {
		otherR = '-';
	}
	if(buf->st_mode & S_IWOTH) {
		otherW = 'w';
	} else {
		otherW= '-';
	}
	if(buf->st_mode & S_IXOTH) {
		otherX = 'x';
	} else {
		otherX = '-';
	}
	sprintf(ligne, "\n user : %c%c%c	group : %c%c%c		other : %c%c%c\n", userR, userW, userX, groupR, groupW, groupX, otherR, otherW, otherX);
	write(1, ligne, TAILLE_BUFFER);
	if(close(fichier1) == -1) {
		perror("Erreur lors de la fermeture\n");
		exit(33);
	}
	fstat(fichier1, buf);
	sprintf(ligne, "%s a une taille de %zu et possede les permissions : \n", *argv, buf->st_size);
	write(1, ligne, TAILLE_BUFFER);
	if (buf->st_mode & S_IRUSR) {
		userR = 'r';
	} else {
		userR = '-';
	}
	if (buf->st_mode & S_IWUSR) {
		userW = 'w';
	} else {
		userW = '-';
	}
	if(buf->st_mode & S_IXUSR) {
		userX = 'x';
	} else {
		userX = '-';
	}
	if(buf->st_mode & S_IRGRP) {
		groupR = 'r';
	} else {
		groupR = '-';
	}
	if(buf->st_mode & S_IWGRP) {
		groupW = 'w';
	} else {
		groupW= '-';
	}
	if(buf->st_mode & S_IXGRP) {
		groupX = 'x';
	} else {
		groupX = '-';
	}
	if(buf->st_mode & S_IROTH) {
		otherR = 'r';
	} else {
		otherR = '-';
	}
	if(buf->st_mode & S_IWOTH) {
		otherW = 'w';
	} else {
		otherW= '-';
	}
	if(buf->st_mode & S_IXOTH) {
		otherX = 'x';
	} else {
		otherX = '-';
	}
	sprintf(ligne, "\n user : %c%c%c	group : %c%c%c		other : %c%c%c\n", userR, userW, userX, groupR, groupW, groupX, otherR, otherW, otherX);
	write(1, ligne, TAILLE_BUFFER);
	if(close(fichier2) == -1) {
		perror("Erreur lors de la fermeture\n");
		exit(33);
	}
	
}
