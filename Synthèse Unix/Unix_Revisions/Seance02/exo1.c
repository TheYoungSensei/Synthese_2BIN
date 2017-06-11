#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <inttypes.h>
#include <stdio.h>
#include <ctype.h>

#define NBMAX_CAR 80
#define SYS(call) ((call) == -1) ? perror(#call ": ERROR"), exit(1) : 0

int getPermissions(int fd, char *nomFichier);

int main(int argc, char **argv) {
	char buffer[NBMAX_CAR];
	int nbLu;
	int maxFile;
	int minFile;
	struct stat *st;
	int test;
	if(argc != 3) {
		perror("monProgramme fichier1 fichier2\n");
		exit(1);
	}
	test = fstat(0, st);
	if(S_ISCHR(st->st_mode)) {
		perror("L'entrée n'est pas l'entrée standard");
		exit(2);
	}  
	SYS(minFile = open(*++argv, O_CREAT|O_TRUNC|O_RDWR));
	SYS(maxFile = open(*++argv, O_CREAT|O_TRUNC|O_RDWR)); 
	while((nbLu = read(0, buffer, NBMAX_CAR))){
		if(nbLu == -1) {
			perror("Erreur Lecture\n");
			exit(3);
		}
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
	getPermissions(minFile, *--argv);
	getPermissions(maxFile, *++argv);
	close(minFile);
	close(maxFile);
}

int getPermissions(int fd, char *nomFichier) {
	char ligne[256];
	struct stat *st;
	char userR = '-';
	char userW = '-';
	char userX = '-';
	char groupR = '-';
	char groupW = '-';
	char groupX = '-';
	char otherR = '-';
	char otherW = '-';
	char otherX = '-';
	if((st = (struct stat *) malloc(sizeof(stat))) == NULL) {
		perror("MALLOC\n");
		exit(3);
	}
	fstat(fd, st);
	sprintf(ligne, "%s a une taille de %zu et possède les permissions : \n", nomFichier, st->st_size);
	write(1, ligne, strlen(ligne));
	if(st->st_mode & S_IRUSR) {
		userR = 'r';
	}
	if(st->st_mode & S_IWUSR) {
		userW = 'w';
	} 
	if(st-> st_mode & S_IXUSR) {
		userX = 'x';
	}
	if(st->st_mode & S_IRGRP) {
		groupR = 'r';
	}
	if(st->st_mode & S_IWGRP) {
		groupW = 'w';
	}
	if(st->st_mode & S_IXGRP) {
		groupX = 'x';
	}
	if(st->st_mode & S_IROTH) {
		otherR = 'r';
	}
	if(st->st_mode & S_IWOTH) {
		otherW = 'w';
	}
	if(st->st_mode & S_IXOTH) {
		otherX = 'x';
	}
	sprintf(ligne, "	user : %c%c%c	group : %c%c%c	other : %c%c%c\n", userR, userW, userX, groupR, groupW, groupX, otherR, otherW, otherX);
	SYS(write(1, ligne, strlen(ligne)));
}
